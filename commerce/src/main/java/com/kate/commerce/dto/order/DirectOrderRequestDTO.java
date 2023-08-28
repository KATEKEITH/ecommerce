package com.kate.commerce.dto.order;

import com.kate.commerce.enums.PayType;

import lombok.Data;

@Data
public class DirectOrderRequestDTO {
    private Long productId;
    private PayType payType;
}
