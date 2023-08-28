package com.kate.admin.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kate.admin.enums.OrderStatus;
import com.kate.admin.enums.PayType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders", schema = "ecommerce")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "pay_type")
    @Enumerated(value = EnumType.STRING)
    private PayType payType;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
