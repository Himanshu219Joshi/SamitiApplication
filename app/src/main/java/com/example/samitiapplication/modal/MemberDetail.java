package com.example.samitiapplication.modal;

import java.io.Serializable;

public class MemberDetail implements Serializable {

    private boolean isPaid = false;

    public boolean isNotPaid() {
        return isNotPaid;
    }

    public void setNotPaid(boolean notPaid) {
        isNotPaid = notPaid;
    }

    private boolean isNotPaid = false;

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean checked) {
        isPaid = checked;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public MemberDetail() {
        this.memberName = "";
        this.loanAmount = "";
        this.investedMoney = "";
        this.fatherName = "";
        this.memberStatus = "";
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getMemberStatus() {
        return memberStatus;
    }

    private final String memberStatus;

//    public LoanDetails getLoanDetails() {
//        return loanDetails;
//    }
//
//    public void setLoanDetails(LoanDetails loanDetails) {
//        this.loanDetails = loanDetails;
//    }
//
//    private LoanDetails loanDetails;
}
