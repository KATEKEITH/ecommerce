package com.kate.commerce.domain;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    private final static String DEFAULT_USER = "system";

    @Column(name = "is_deleted")
    protected boolean isDeleted = false;

    @Column(name = "created_by")
    protected String createdBy = DEFAULT_USER;

    @Column(name = "created_at")
    protected OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_by")
    private String updatedBy = DEFAULT_USER;

    @Column(name = "updated_at")
    protected OffsetDateTime updatedAt = OffsetDateTime.now();

}
