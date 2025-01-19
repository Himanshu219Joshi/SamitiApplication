package com.example.samitiapplication.modal;

public class MemberDetail {

    public MemberDetail(String memberName, String loanAmount, String investedMoney, String fatherName) {
        this.memberName = memberName;
        this.loanAmount = loanAmount;
        this.investedMoney = investedMoney;
        this.fatherName = fatherName;
    }

    private final String memberName;

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

    public String getLoanAmount() {
        return loanAmount;
    }

    public String getInvestedMoney() {
        return investedMoney;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getIdAndMemberList() {
        return this.memberId +" "+this.getMemberName();
    }

    private String memberId;
    private final String loanAmount;
    private final String investedMoney;
}
