package com.example.eTransact.Model;

import com.example.eTransact.utility.MyConverter;
import com.example.eTransact.utility.MySequence;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.javamoney.moneta.Money;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.UUID;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @MySequence(initialValue = 123456789)
    @Column(unique = true)
    private String accountNo;
    @Convert(converter = MyConverter.class)
    @Access(value = AccessType.PROPERTY)
    private Money balance;
    @Version
    private long version;

    public Account(){

    }

    public UUID getId() {
        return id;
    }


    public void setBalance(@NotNull Money money){
        if(money.isNegativeOrZero())
            throw new IllegalStateException("Money must be greater than zero");
        if(!money.getCurrency().equals(getBalance().getCurrency()))
            throw new InputMismatchException("Expected currency  should be : " + getBalance().getCurrency().getCurrencyCode() + " but got " + money.getCurrency().getCurrencyCode() + " instead");
        this.balance = money;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Money getBalance() {
        if(this.balance == null)
            return Money.of(BigDecimal.ZERO, "USD");
        return this.balance;
    }

    public long getVersion() {
        return this.version;
    }
}
