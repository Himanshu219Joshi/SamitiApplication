package com.example.samitiapplication.modal;

import java.util.List;

public class LoanDetails {

    private String loanAmount;

    public String getDate() {
        return date;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public String getEmiAmount() {
        return emiAmount;
    }

    public String getFinalAmountWithInterest() {
        return finalAmountWithInterest;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public String getMemberDetails() {
        return memberDetails;
    }

    private String date;
    private String emiAmount;
    private String finalAmountWithInterest;
    private String totalInterest;

    private String memberDetails;

    public List<MemberDetail> getGuarantors() {
        return guarantors;
    }

    public void setGuarantors(List<MemberDetail> guarantors) {
        this.guarantors = guarantors;
    }

    private List<MemberDetail> guarantors;

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    private String loanStatus;

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    private LoanDetails loanDetails;

}
