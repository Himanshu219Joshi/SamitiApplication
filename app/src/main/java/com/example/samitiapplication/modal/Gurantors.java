package com.example.samitiapplication.modal;

public class Gurantors {

    private long memberId;
    private String memberName;
    private String fatherName;
    private long loanAmount;
    private long investedMoney;

    public String getLoanDetails() {
        return loanDetails;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMemberName() {
        return memberName;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getInvestedMoney() {
        return investedMoney;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    private String memberStatus;
    private String loanDetails;
}
