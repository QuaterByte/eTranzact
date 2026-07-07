package com.example.eTransact.exception;

public class CurrencyMisMatchException extends RuntimeException {
    public CurrencyMisMatchException(String message) {
        super(message);
    }
}
