package com.kate.commerce.dto.order;

import com.kate.commerce.enums.PayType;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private PayType payType;
}
