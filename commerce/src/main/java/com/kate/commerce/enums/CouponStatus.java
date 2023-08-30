package com.kate.commerce.enums;

public enum CouponStatus {
    UNUSED("미사용"),
    USED("사용");

    private String description;

    CouponStatus(String description) {
        this.description = description;
    }
}
