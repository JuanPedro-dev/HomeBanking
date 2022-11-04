package com.santander.hombanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

// @Entity
@Entity(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private TransactionType type;
    private String description;
    @Column(name="creation_date")
    private LocalDateTime date;
    private double amount;

    //
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
    //


    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction() {
    }

    public Transaction(TransactionType type, String description, LocalDateTime date, double amount, Account account) {
        this.type = type;
        this.description = description;
        this.date = date;
        this.amount = amount;
//        account.addTransaction(this);
        this.account = account;
    }

    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public void setId(long id) {
        this.id = id;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }



}
