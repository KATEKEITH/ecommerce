package com.kate.commerce.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kate.commerce.domain.cart.CartItemProduct;
import com.kate.commerce.domain.coupon.Coupon;
import com.kate.commerce.domain.customer.Customer;
import com.kate.commerce.domain.order.Order;
import com.kate.commerce.domain.order.OrderItem;
import com.kate.commerce.domain.order.OrderItemDetail;
import com.kate.commerce.domain.order.OrderSheet;
import com.kate.commerce.domain.product.Product;
import com.kate.commerce.dto.customer.CustomerDTO;
import com.kate.commerce.enums.PayType;
import com.kate.commerce.exception.NotFoundProductException;
import com.kate.commerce.repository.cart.CartItemRepository;
import com.kate.commerce.repository.customer.CustomerRepository;
import com.kate.commerce.repository.order.OrderItemRepository;
import com.kate.commerce.repository.order.OrderRepository;
import com.kate.commerce.service.cart.CartQueryService;
import com.kate.commerce.service.coupon.CouponQueryService;
import com.kate.commerce.service.product.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    public static final int DIRECT_ORDER_QUANTITY = 1;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CustomerRepository customerRepository;

    private final ProductService productService;

    private final OrderServiceSupport orderServiceSupport;

    private final CartQueryService cartQueryService;

    private final CouponQueryService couponQueryService;

    public Long order(Customer customer, List<Long> cartItems, PayType payType, String couponCode) {

        // 상품 정보 -> Elasticsearch
        List<CartItemProduct> targetCartItems = cartQueryService.findAllCartItems(cartItems);

        // 쿠폰 정보 -> Mysql
        Coupon coupon = couponQueryService.findByCouponCode(couponCode);

        Order savedOrder = orderServiceSupport.order(
                customer,
                targetCartItems,
                payType,
                coupon);

        return savedOrder.getOrderId();
    }

    private Order createOrder(Customer customer, BigDecimal totalAmount, PayType payType) {
        Order order = new Order();
        order.setCustomerId(customer.getCustomerId());
        order.setDeliveryAddress(customer.getAddress());
        order.setAmount(totalAmount);
        order.setPayType(payType);
        return this.orderRepository.save(order);
    }

    private List<OrderItem> createOrderItems(List<CartItemProduct> targetCartItems, Order order) {
        List<OrderItem> orderItems = targetCartItems.stream().map(cartItemProduct -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(cartItemProduct.getProductId());
            orderItem.setPrice(cartItemProduct.getProductPrice());
            orderItem.setQuantity(cartItemProduct.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());
        List<OrderItem> savedOrderItems = this.orderItemRepository.saveAll(orderItems);
        return savedOrderItems;
    }

    public Optional<OrderSheet> createOrderSheet(Long orderId) {
        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }
        Order order = optionalOrder.get();
        Optional<Customer> optionalCustomer = this.customerRepository.findById(order.getCustomerId());
        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }
        ;

        List<OrderItemDetail> allOrderItemDetails = orderItemRepository.findAllOrderItemDetails(order.getOrderId());
        OrderSheet orderSheet = OrderSheet.of(
                orderId,
                order.getCreatedAt(),
                CustomerDTO.of(optionalCustomer.get()),
                order.getDeliveryAddress(),
                order.getAmount(),
                allOrderItemDetails);
        return Optional.of(orderSheet);
    }

    public Long directOrder(Customer customer, Long productId, PayType payType) {

        Optional<Product> optionalProduct = this.productService.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundProductException("구매하려는 상품 정보를 찾을 수 없습니다.");
        }
        Product product = optionalProduct.get();

        // Total Amount
        BigDecimal totalAmount = product.getPrice();

        // Order
        Order savedOrder = createOrder(customer, totalAmount, payType);

        // Order Item
        OrderItem orderItem = createOrderItem(savedOrder.getOrderId(), product);
        this.orderItemRepository.save(orderItem);

        return savedOrder.getOrderId();
    }

    private OrderItem createOrderItem(Long orderId, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setProductId(product.getId());
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(DIRECT_ORDER_QUANTITY);
        return orderItem;
    }

}
