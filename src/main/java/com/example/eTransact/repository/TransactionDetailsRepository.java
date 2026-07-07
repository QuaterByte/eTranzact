package com.example.eTransact.repository;

import com.example.eTransact.Model.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, UUID> {
}
