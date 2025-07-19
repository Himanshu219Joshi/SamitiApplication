package com.example.samitiapplication.modal.members;


public class MemberDetailsWithoutLoanDetails {

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


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getMemberId() {
        return memberId;
    }
//
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
//
    public String getMemberName() {
        return memberName;
    }
//
//    public void setMemberName(String memberName) {
//        this.memberName = memberName;
//    }
//
    public String getFatherName() {
        return fatherName;
    }
//
//    public void setFatherName(String fatherName) {
//        this.fatherName = fatherName;
//    }
//
    public long getLoanAmount() {
        return loanAmount;
    }
//
//    public void setLoanAmount(long loanAmount) {
//        this.loanAmount = loanAmount;
//    }
//
    public long getInvestedMoney() {
        return investedMoney;
    }
//
//    public void setInvestedMoney(long investedMoney) {
//        this.investedMoney = investedMoney;
//    }
//
    public String getMemberStatus() {
        return memberStatus;
    }
//
//    public void setMemberStatus(String memberStatus) {
//        this.memberStatus = memberStatus;
//    }
//
//
    private String _id;
    private long memberId;
    private String memberName;
    private String fatherName;
    private long loanAmount;
    private long investedMoney;
    private String memberStatus;
}
