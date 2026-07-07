package com.example.eTransact.service;

import com.example.eTransact.Model.Account;
import com.example.eTransact.Model.Beneficiary;
import com.example.eTransact.Model.TransactionDetails;
import com.example.eTransact.Model.TransferRequest;
import com.example.eTransact.exception.AccountNotFoundException;
import com.example.eTransact.exception.CurrencyMisMatchException;
import com.example.eTransact.exception.InsufficientFundsException;
import com.example.eTransact.exception.MonetaryAmountException;
import com.example.eTransact.repository.AccountRepository;
import com.example.eTransact.repository.TransactionDetailsRepository;
import com.example.eTransact.utility.AccountDebitPolicy;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransferServiceImpl implements TransferService{

    private final AccountRepository accountRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    public TransferServiceImpl(AccountRepository accountRepository, TransactionDetailsRepository transactionDetailsRepository){
        this.accountRepository = accountRepository;
        this.transactionDetailsRepository = transactionDetailsRepository;
    }


    @Override
    @Transactional
    public TransactionDetails transfer(TransferRequest transferRequest) {

        if(transferRequest == null)
            throw new IllegalArgumentException("transferRequest can't be null");

        if(transferRequest.getMoney() == null )
            throw new MonetaryAmountException();

        if(transferRequest.getMoney().isNegativeOrZero())
            throw new IllegalStateException("money must be greater than 0");

        Beneficiary beneficiary = transferRequest.getBeneficiary();
        Optional<Account> senderAccount = accountRepository.findByAccountnumber(transferRequest.getSenderAccountNumber());
        Optional<Account> receiverAccount = accountRepository.findByAccountnumber(beneficiary.getAccountNumber());
        if(receiverAccount.isEmpty())
            throw new AccountNotFoundException("Can't find beneficiary account:  " + beneficiary.getAccountNumber());
        if(senderAccount.isEmpty())
            throw  new AccountNotFoundException("Can't find sender account: " + transferRequest.getSenderAccountNumber());


        Account sender = senderAccount.get();
        Account receiver = receiverAccount.get();

        if(currencyCodeValidaton(sender.getBalance(), transferRequest.getMoney()))
            throw new CurrencyMisMatchException("transfer request money currency code doesn't match sender currency code");
        if(currencyCodeValidaton(sender.getBalance(), receiver.getBalance()))
            throw new CurrencyMisMatchException("Invalid operation: receiver account currency is of different value");

        if(!AccountDebitPolicy.isDebitable(sender, transferRequest.getMoney()))
            throw  new InsufficientFundsException();

        sender.setBalance(sender.getBalance().subtract(transferRequest.getMoney()));
        receiver.setBalance(receiver.getBalance().add(transferRequest.getMoney()));


        TransactionDetails details = new TransactionDetails();
        details.setLocalDateTime(LocalDateTime.now());
        details.setAmount(transferRequest.getMoney());
        details.setReceivername(transferRequest.getBeneficiary().getAccountName());
        details.setSendername(sender.getUser().fullname());

        accountRepository.save(sender);
        accountRepository.save(receiver);
        return  transactionDetailsRepository.save(details);

    }

    private boolean currencyCodeValidaton(Money money, Money Money2){
        return !money.getCurrency().equals(money.getCurrency());
    }
}
