package com.kate.commerce.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

import com.kate.commerce.enums.DeliveryType;

@Data
@AllArgsConstructor
public class OrderItemDetail {
    private Long orderId;
    private Long orderItemId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productImageUrl;
    private int quantity;
    private DeliveryType deliveryType;
}
