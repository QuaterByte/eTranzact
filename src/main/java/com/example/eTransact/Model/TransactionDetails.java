package com.example.eTransact.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime localDateTime;
    private String sendername;
    private String receivername;
    private Money amount;


    public UUID getId() {
        return id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getReceivername() {
        return receivername;
    }

    public String getSendername() {
        return sendername;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public Money getAmount() {
        return amount;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }
}
