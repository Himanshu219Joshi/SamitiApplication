package com.example.samitiapplication.modal;

public class Summary {
    public Summary(long lentAmount, long totalAmount, long balanceAmount, long interestAccrued, long penaltyAmount) {
        this.lentAmount = lentAmount;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.interestAccrued = interestAccrued;
        this.penaltyAmount = penaltyAmount;
    }

    private long totalAmount;

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
