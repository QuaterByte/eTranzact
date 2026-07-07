package com.example.eTransact.utility;

import com.example.eTransact.Model.Account;
import org.javamoney.moneta.Money;

public class AccountDebitPolicy {
    public static  boolean isDebitable(Account account, Money money){
        return account.getBalance().isGreaterThanOrEqualTo(money);
    }
}
