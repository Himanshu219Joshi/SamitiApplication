package com.example.samitiapplication.modal.members;

public class MemberSummary {

    public String getTotalInvestedMoney() {
        return totalInvestedMoney;
    }

    public void setTotalInvestedMoney(String totalInvestedMoney) {
        this.totalInvestedMoney = totalInvestedMoney;
    }

    public String getTotalLoanTaken() {
        return totalLoanTaken;
    }

    public void setTotalLoanTaken(String totalLoanTaken) {
        this.totalLoanTaken = totalLoanTaken;
    }

    public String getTotalInterestEarned() {
        return totalInterestEarned;
    }

    public void setTotalInterestEarned(String totalInterestEarned) {
        this.totalInterestEarned = totalInterestEarned;
    }

    private String totalInvestedMoney;
    private String totalLoanTaken;
    private String totalInterestEarned;
}
