package com.kate.commerce.domain.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.*;

import com.kate.commerce.domain.BaseEntity;

@Entity
@Table(name = "order_items", schema = "ecommerce")
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity = 0;

    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;
}
