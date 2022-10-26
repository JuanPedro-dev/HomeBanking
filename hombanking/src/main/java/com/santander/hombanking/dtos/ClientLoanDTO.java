package com.santander.hombanking.dtos;

import com.santander.hombanking.models.Client;
import com.santander.hombanking.models.ClientLoan;
import com.santander.hombanking.models.Loan;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String name;
    private double amount;  // Monto del prestamo
    private int payments;   // Coutas...

    // Constructor
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    // Gettes
    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public int getPayments() {
        return payments;
    }
    public long getLoanId() {
        return loanId;
    }
    public String getName() {
        return name;
    }


}
