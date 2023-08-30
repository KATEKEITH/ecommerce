package com.kate.commerce.service.coupon;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kate.commerce.domain.coupon.Coupon;
import com.kate.commerce.repository.coupon.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public Coupon findByCouponCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode);
    }
}
