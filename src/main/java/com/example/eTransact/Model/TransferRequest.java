package com.example.eTransact.Model;

import org.javamoney.moneta.Money;

public class TransferRequest {

    private final Beneficiary beneficiary;
    private final Money money;
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
