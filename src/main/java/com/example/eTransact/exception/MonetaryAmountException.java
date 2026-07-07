package com.example.eTransact.exception;

public class MonetaryAmountException extends RuntimeException {
    public MonetaryAmountException() {
        super("Money can't be null");
    }
}
