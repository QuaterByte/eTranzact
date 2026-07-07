package com.example.eTransact.service;

import com.example.eTransact.Model.TransactionDetails;
import com.example.eTransact.Model.TransferRequest;

public interface TransferService {

    public TransactionDetails transfer(TransferRequest transferRequest);
}
