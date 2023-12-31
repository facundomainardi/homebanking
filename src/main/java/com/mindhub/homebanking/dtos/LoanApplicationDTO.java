package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private Long loanId;
    private Double amount;
    private Integer payments;
    private String toAccountNumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long loanId, Double amount, Integer payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    @Override
    public String toString() {
        return "LoanApplicationDTO{" +
                "loanId=" + loanId +
                ", amount=" + amount +
                ", payments=" + payments +
                ", toAccountNumber='" + toAccountNumber + '\'' +
                '}';
    }
}
