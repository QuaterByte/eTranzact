package com.example.eTransact.exception;

public class InsufficientFundsException extends RuntimeException {
    ;
    public InsufficientFundsException()
    {
        super("Insufficient funds");
    }


}
