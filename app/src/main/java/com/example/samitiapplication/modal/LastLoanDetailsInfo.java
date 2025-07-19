package com.example.samitiapplication.modal;

import java.util.List;

public class LastLoanDetailsInfo  {

    public String get_id() {
        return _id;
    }

    private String _id;
    private long loanAmount;
    private String date;
    private long emiAmount;
    private long finalAmountWithInterest;

    private long totalInterest;

    private List<Gurantors> guarantors;

    public String getLoanStatus() {
        return loanStatus;
    }

    public String getDate() {
        return date;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public long getEmiAmount() {
        return emiAmount;
    }

    public long getFinalAmountWithInterest() {
        return finalAmountWithInterest;
    }

    public long getTotalInterest() {
        return totalInterest;
    }

    public List<Gurantors> getGuarantors() {
        return this.guarantors;
    }

    public LastMemberDetails getMemberDetails() {
        return memberDetails;
    }

    private String loanStatus;

    private LastMemberDetails memberDetails;

    public long getLoanAmountRecovered() {
        return loanAmountRecovered;
    }

    private long loanAmountRecovered;

    public long getInterestAccrued() {
        return interestAccrued;
    }

    private long interestAccrued;

}