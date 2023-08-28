package com.kate.commerce.domain.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.kate.commerce.enums.PayType;

@Data(staticConstructor = "of")
public class OrderResult {
    private final Long orderId;
    private final BigDecimal totalAmount;
    private final List<OrderItemDetail> orderItemDetails;
    private final PayType payType;
    private final String address;
}
