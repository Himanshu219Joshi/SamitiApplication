package com.example.samitiapplication.modal;

import java.util.List;

public class LoanDetail {

    private String _id;
    private String memberName;

    public long getLoanAmountRecovered() {
        return loanAmountRecovered;
    }

    public void setLoanAmountRecovered(long loanAmountRecovered) {
        this.loanAmountRecovered = loanAmountRecovered;
    }

    public long getInterestAccrued() {
        return interestAccrued;
    }

    public void setInterestAccrued(long interestAccrued) {
        this.interestAccrued = interestAccrued;
    }

    private long loanAmountRecovered;

    private long interestAccrued;

    private long emiAmount;

    private long finalAmountWithInterest;

    private List<MemberDetail> guarantors;
    public MemberDetail getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetail(MemberDetail memberDetails) {
        this.memberDetails = memberDetails;
    }

    private MemberDetail memberDetails;

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

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    private String loanStatus;

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    private LoanDetail loanDetails;

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

    public LoanDetail getLoanDetails() {
        return loanDetails;
    }

    public List<MemberDetail> getGuarantors() {
        return guarantors;
    }

    public void setGuarantors(List<MemberDetail> guarantors) {
        this.guarantors = guarantors;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

