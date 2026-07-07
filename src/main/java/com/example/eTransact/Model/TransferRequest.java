package com.example.eTransact.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.javamoney.moneta.Money;

public class TransferRequest {

    @NotNull
    private final Beneficiary beneficiary;
    @NotNull
    private final Money money;
    @NotBlank
    private String senderAccountNumber;

    public TransferRequest(Beneficiary beneficiary, Money money, String senderAccountNumber){
        this.beneficiary = beneficiary;
        this.money = money;
        this.senderAccountNumber = senderAccountNumber;
    }

    public TransferRequest(Beneficiary beneficiary, Money money){
        this.beneficiary = beneficiary;
        this.money = money;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public Money getMoney() {
        return money;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

}
