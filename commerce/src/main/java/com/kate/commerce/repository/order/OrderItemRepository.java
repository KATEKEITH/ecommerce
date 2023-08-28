package com.kate.commerce.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kate.commerce.domain.order.OrderItem;
import com.kate.commerce.domain.order.OrderItemDetail;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "SELECT new com.kate.commerce.domain.order.OrderItemDetail(oi.orderId, oi.orderItemId, oi.productId, p.productName, p.price, p.imageUrl, oi.quantity, p.deliveryType) "
            +
            "FROM OrderItem oi " +
            "INNER JOIN Product p ON oi.productId = p.id " +
            "WHERE oi.orderId IN (:orderId) AND oi.isDeleted = false"

    )
    List<OrderItemDetail> findAllOrderItemDetails(@Param("orderId") Long orderId);
}
