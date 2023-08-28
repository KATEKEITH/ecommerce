package com.kate.admin.exception;

public class NotFoundCustomerException extends RuntimeException {
    public NotFoundCustomerException(String message) {
        super(message);
    }
}
