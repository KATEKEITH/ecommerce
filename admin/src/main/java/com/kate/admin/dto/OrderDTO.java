package com.kate.admin.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.kate.admin.enums.OrderStatus;
import com.kate.admin.enums.PayType;

import lombok.Data;

@Data(staticConstructor = "of")
public class OrderDTO {
    private final Long orderId;
    private final BigDecimal amount;
    private final Long customerId;
    private final String customerName;
    private final PayType payType;
    private final OrderStatus orderStatus;
    private final OffsetDateTime orderDate;
}
