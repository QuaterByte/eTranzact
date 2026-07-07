package com.example.eTransact.controller.advice;

import com.example.eTransact.exception.AccountNotFoundException;
import com.example.eTransact.exception.CurrencyMisMatchException;
import com.example.eTransact.exception.InsufficientFundsException;
import com.example.eTransact.exception.MonetaryAmountException;
import com.example.eTransact.response.FormErrorResponse;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(exception = AccountNotFoundException.class)
    public ResponseEntity<FormErrorResponse> accounNotFound(Exception ex){
        return new ResponseEntity<>(new FormErrorResponse(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(exception = CurrencyMisMatchException.class)
    public ResponseEntity<FormErrorResponse> currencyMisMatch(Exception e){
        return new ResponseEntity<>(new FormErrorResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(exception = InsufficientFundsException.class)
    public ResponseEntity<FormErrorResponse> insufficientFund(Exception ex){
        return new ResponseEntity<>(new FormErrorResponse(ex.getMessage(), null), HttpStatus.UNPROCESSABLE_CONTENT);
    }
    @ExceptionHandler(exception = MonetaryAmountException.class)
    public ResponseEntity<FormErrorResponse> monetaryexception(Exception ex){
        return new ResponseEntity<>(new FormErrorResponse(ex.getMessage(), null), HttpStatus.UNPROCESSABLE_CONTENT);
    }
    @ExceptionHandler(exception = OptimisticLockingFailureException.class)
    public ResponseEntity<FormErrorResponse> optimistic(){
        return new ResponseEntity<>(new FormErrorResponse("an error occured, try again", null), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<FormErrorResponse> sta(Exception e){
        return new ResponseEntity<>(new FormErrorResponse(e.getMessage(), null), HttpStatus.UNPROCESSABLE_CONTENT);
    }


}
