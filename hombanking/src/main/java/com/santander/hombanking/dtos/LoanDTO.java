package com.santander.hombanking.dtos;

import com.santander.hombanking.models.ClientLoan;
import com.santander.hombanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {
    private long loanId;
    private String name;
    private double amount;
    private List<Integer> payments = new ArrayList<>();
   private Set<ClientLoanDTO> clientLoanDTOS = new HashSet<>();

    // Constructor
    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.loanId = loan.getId();
        this.name = loan.getName();
        this.amount = loan.getMaxAmount();
        this.payments = loan.getPayments();
//        this.clientLoanDTOS = loan.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
    }

    // Getters no voy a modificar al DTO, solo junta datos
    public long getId() {
        return loanId;
    }
    public String getName() {
        return name;
    }
    public double getMaxAmount() {
        return amount;
    }
    public List<Integer> getPayments() {
        return payments;
    }



}
