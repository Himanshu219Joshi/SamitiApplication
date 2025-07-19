package com.example.samitiapplication.modal;

public class LastLoanDetails {
   private String memberName;
   private LastLoanNewSchema loanDetails;
   private String memberId;

    public LastLoanNewSchema getLoanDetails() {
        return loanDetails;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public String getMemberId() {
        return this.memberId;
    }


    public String get_id() {
        return _id;
    }

    private String _id;
}
