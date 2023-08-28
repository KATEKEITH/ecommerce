package com.kate.commerce.domain.cart;

import java.math.BigDecimal;

import com.kate.commerce.enums.DeliveryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemProduct {

    private Long cartId;

    private Long cartItemId;

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private String productImageUrl;

    private int quantity;

    private DeliveryType deliveryType;

}
