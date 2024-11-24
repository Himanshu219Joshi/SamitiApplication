package com.example.samitiapplication.modal;

public class NewLoanDetail {
    private long totalAmount;

    public long getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(long emiAmount) {
        this.emiAmount = emiAmount;
    }

    private long emiAmount;
    private long loanAmount;

    public long getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(long penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    private long penaltyAmount;
    private int memberId;

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
