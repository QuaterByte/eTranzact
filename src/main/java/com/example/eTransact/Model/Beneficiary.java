package com.example.eTransact.Model;

import java.util.Objects;

public class Beneficiary {

    private final String accountNumber;
    private final String accountName;
    private final String bankName;

    public Beneficiary(String accountNumber, String accountName, String bankName){
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return  true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Beneficiary beneficiary = (Beneficiary) obj;
        return Objects.equals(this, obj);
    }
}
