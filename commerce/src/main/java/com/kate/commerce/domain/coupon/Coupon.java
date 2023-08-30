package com.kate.commerce.domain.coupon;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kate.commerce.enums.CouponStatus;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Table(name = "coupon")
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime useDate;

    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;

    @Builder
    public Coupon(@NonNull String title, String content, LocalDateTime useDate, CouponStatus status) {
        this.title = title;
        this.content = content;
        this.useDate = useDate;
        this.status = status;
    }
}
