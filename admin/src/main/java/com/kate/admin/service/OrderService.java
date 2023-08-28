package com.kate.admin.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kate.admin.repository.CustomerRepository;
import com.kate.admin.repository.OrderRepository;

import lombok.AllArgsConstructor;

import com.kate.admin.domain.Customer;
import com.kate.admin.domain.Order;
import com.kate.admin.domain.OrderDetailView;
import com.kate.admin.dto.OrderDTO;
import com.kate.admin.exception.NotFoundCustomerException;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    public List<OrderDetailView> findAllOrderDetailView() {
        return orderRepository.findAllOrderDetailView();
    }

    public Optional<OrderDTO> findById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            return Optional.ofNullable(null);
        }
        Order order = optionalOrder.get();
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new NotFoundCustomerException("주문 고객 정보를 찾을 수 없습니다."));
        OrderDTO orderDTO = OrderDTO.of(order.getOrderId(), order.getAmount(), order.getCustomerId(),
                customer.getCustomerName(), order.getPayType(), order.getOrderStatus(), order.getCreatedAt());
        return Optional.of(orderDTO);
    }
}
