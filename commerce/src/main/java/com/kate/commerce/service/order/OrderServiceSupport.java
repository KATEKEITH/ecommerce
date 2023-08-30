package com.kate.commerce.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kate.commerce.domain.cart.CartItemProduct;
import com.kate.commerce.domain.coupon.Coupon;
import com.kate.commerce.domain.customer.Customer;
import com.kate.commerce.domain.order.Order;
import com.kate.commerce.domain.order.OrderItem;
import com.kate.commerce.enums.CouponStatus;
import com.kate.commerce.enums.PayType;
import com.kate.commerce.exception.InvalidValueCouponException;
import com.kate.commerce.exception.OutOfStockException;
import com.kate.commerce.repository.order.OrderItemRepository;
import com.kate.commerce.repository.order.OrderRepository;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceSupport {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    public Order order(Customer customer, List<CartItemProduct> targetCartItems, PayType payType, Coupon coupon) {

        // 상품 재고가 있는지 없는 경우 예외 처리
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItemProduct cartItemProduct : targetCartItems) {
            if (cartItemProduct.getStockQuantity() < cartItemProduct.getQuantity()) {
                throw new OutOfStockException(
                        "상품의 재고가 부족합니다. ( 현재 재고 수량 : " + cartItemProduct.getStockQuantity() + ")");
            }

            totalAmount = totalAmount
                    .add(cartItemProduct.getProductPrice().multiply(new BigDecimal(cartItemProduct.getQuantity())));
        }

        // 쿠폰 정보 조회하여 적용 가능한 상품 인지 확인 만료된 쿠폰인 경우 예외 처리
        if (coupon.getStatus().equals(CouponStatus.USED)) {
            throw new InvalidValueCouponException("만료된 쿠폰입니다");
        }

        Order order = new Order();
        order.setCustomerId(customer.getCustomerId());
        order.setDeliveryAddress(customer.getAddress());
        order.setAmount(totalAmount);
        order.setPayType(payType);
        orderRepository.save(order);

        List<OrderItem> orderItems = targetCartItems.stream().map(cartItemProduct -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(cartItemProduct.getProductId());
            orderItem.setPrice(cartItemProduct.getProductPrice());
            orderItem.setQuantity(cartItemProduct.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());
        List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItems);

        return order;
    }

}
