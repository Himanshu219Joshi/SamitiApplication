package com.example.samitiapplication.modal;

public class LoanDetail {

    private String memberName;

    private long emiAmount;

    private long finalAmountWithInterest;

    public MemberDetail getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetail(MemberDetail memberDetails) {
        this.memberDetails = memberDetails;
    }

    private MemberDetail memberDetails;

    public LoanDetail getLoanDetail() {
        return loanDetail;
    }

    public void setLoanDetail(LoanDetail loanDetail) {
        this.loanDetail = loanDetail;
    }

    private LoanDetail loanDetail;

    public long getFinalAmountWithInterest() {
        return finalAmountWithInterest;
    }

    public void setFinalAmountWithInterest(long finalAmountWithInterest) {
        this.finalAmountWithInterest = finalAmountWithInterest;
    }

    public long getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(long totalInterest) {
        this.totalInterest = totalInterest;
    }

    private long totalInterest;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    private String fatherName;

    private String memberId;

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String loanAmount;
    String date;

    public long getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(long emiAmount) {
        this.emiAmount = emiAmount;
    }
}

