package com.kate.commerce.domain.customer;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kate.commerce.enums.CustomerPermission;
import com.kate.commerce.enums.ECommerceRole;

import lombok.Data;

@Entity
@Table(name = "customers", schema = "ecommerce")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private ECommerceRole role;

    @Column(name = "permission")
    @Enumerated(value = EnumType.STRING)
    private CustomerPermission permission;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "is_activated")
    private boolean isActivated = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}
