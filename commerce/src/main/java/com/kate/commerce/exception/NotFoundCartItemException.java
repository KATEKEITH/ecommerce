package com.kate.commerce.exception;

public class NotFoundCartItemException extends RuntimeException {
    public NotFoundCartItemException(String message) {
        super(message);
    }
}
