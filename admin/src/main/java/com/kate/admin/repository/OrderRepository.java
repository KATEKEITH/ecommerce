package com.kate.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kate.admin.domain.Order;
import com.kate.admin.domain.OrderDetailView;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT new com.fastcampus.ecommerce.admin.domain.order.OrderDetailView(o.orderId, o.customerId, c.customerName, o.amount, o.orderStatus, o.payType, o.createdAt) "
            +
            "FROM Order o JOIN Customer c ON o.customerId = c.customerId")
    List<OrderDetailView> findAllOrderDetailView();
}
