package com.santander.hombanking.dtos;

public class LoanApplicationDTO {
    private Long loanId;
    private double amount;
    private Integer payments;
    private String toAccountNumber;

    public LoanApplicationDTO() {
    }
    public LoanApplicationDTO(Long loanId, double amount, Integer payment, String accountLoan) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payment;
        this.toAccountNumber = accountLoan;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }


}
