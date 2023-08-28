package com.kate.admin.domain;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.kate.admin.enums.CustomerGrade;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers", schema = "ecommerce")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long customerId;

    @Column(name = "name")
    private String customerName;

    @Column(name = "age")
    private int age;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private CustomerGrade grade;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @CreatedDate
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")

    private OffsetDateTime updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;

}
