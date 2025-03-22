package com.example.samitiapplication.modal;

public class LastLoanDetails {
   private String memberName;
   private String memberId;

    public String getMemberName() {
        return memberName;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public String getMemberId() {
        return memberId;
    }

    private LoanDetails loanDetails;
}
