package com.assessment.bookstore.exception;

public class OrderPaidException extends RuntimeException {
    public OrderPaidException(String message) {
        super(message);
    }
}
