package com.example.samitiapplication.modal;

public class Person {

    public Person(String memberName, String loanAmount, String investedMoney, String fatherName) {
        this.memberName = memberName;
        this.loanAmount = loanAmount;
        this.investedMoney = investedMoney;
        this.fatherName = fatherName;
    }

    private String memberName;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    private String fatherName;

    public String getMemberName() {
        return memberName;
    }

    public String getId() {
        return id;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public String getInvestedMoney() {
        return investedMoney;
    }

    private String id;
    private String loanAmount;
    private String investedMoney;
}
