package com.kate.admin.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.kate.admin.enums.OrderStatus;
import com.kate.admin.enums.PayType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailView {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private BigDecimal amount;
    private OrderStatus orderStatus;
    private PayType payType;
    private OffsetDateTime createdAt;
}
