package com.example.eTransact.controller;

import com.example.eTransact.DTO.AccountDTO;
import com.example.eTransact.Model.Account;
import com.example.eTransact.Model.TransferRequest;
import com.example.eTransact.repository.AccountRepository;
import com.example.eTransact.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequestMapping(path = "/account")
@RestController
public class AccountController {

    private final AccountRepository accountRepository;
    private final TransferService transferService;

    public AccountController(AccountRepository accountRepository, TransferService transferService){
        this.accountRepository = accountRepository;
        this.transferService = transferService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") UUID id){
        Optional<Account> account  = accountRepository.findById(id);
        if(account.isEmpty())
            return ResponseEntity.notFound().build();
        Account account1 = account.get();
        AccountDTO dto = new AccountDTO();
        dto.setAccountName(account1.getUser().fullname());
        dto.setAccountNumber(account1.getAccountNo());
        dto.setBalance(account1.getBalance());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/intrabanktransfer")
    public ResponseEntity<?> onNetTransfer(TransferRequest request){

        try{
            transferService.transfer(request);
        } catch (RuntimeException e) {
            throw e;
        }
        return ResponseEntity.noContent().build();
    }


}
