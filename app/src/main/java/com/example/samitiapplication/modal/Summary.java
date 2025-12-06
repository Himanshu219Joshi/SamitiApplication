package com.example.samitiapplication.modal;

public class Summary {
    public Summary(long lentAmount, long totalAmount, long balanceAmount, long interestAccrued, long penaltyAmount, long loanAmountRecovered) {
        this.lentAmount = lentAmount;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.interestAccrued = interestAccrued;
        this.penaltyAmount = penaltyAmount;
        this.loanAmountRecovered = loanAmountRecovered;
    }

    private long totalAmount;

    public long getLoanAmountRecovered() {
        return loanAmountRecovered;
    }

    public void setLoanAmountRecovered(long loanAmountRecovered) {
        this.loanAmountRecovered = loanAmountRecovered;
    }

    private long loanAmountRecovered;

    public long getLentAmount() {
        return lentAmount;
    }

    public void setLentAmount(long lentAmount) {
        this.lentAmount = lentAmount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    private long lentAmount;
    private long balanceAmount;

    public long getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(long penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    private long penaltyAmount;

    public long getInterestAccrued() {
        return interestAccrued;
    }

    public void setInterestAccrued(long interestAccrued) {
        this.interestAccrued = interestAccrued;
    }

    private long interestAccrued;
}
