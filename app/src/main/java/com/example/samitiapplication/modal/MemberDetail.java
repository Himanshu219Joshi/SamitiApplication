package com.example.samitiapplication.modal;

import java.io.Serializable;

public class MemberDetail {

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
        return this.memberName;
    }

    public String getLoanAmount() {
        return this.loanAmount;
    }

    public String getInvestedMoney() {
        return this.investedMoney;
    }

    public String getMemberId() {
        return this.memberId;
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

    public LastLoanDetailsInfo getLoanDetails() {
        return this.loanDetails;
    }

    public LastLoanDetailsInfo loanDetails;

}
