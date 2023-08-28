package com.kate.commerce.domain.order;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import com.kate.commerce.domain.BaseEntity;
import com.kate.commerce.enums.PayType;

@Entity
@Table(name = "orders", schema = "ecommerce")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "pay_type")
    @Enumerated(value = EnumType.STRING)
    private PayType payType;

}
