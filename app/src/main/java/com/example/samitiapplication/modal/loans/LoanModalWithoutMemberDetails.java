package com.example.samitiapplication.modal.loans;

import com.example.samitiapplication.modal.Gurantors;
import com.example.samitiapplication.modal.members.MemberModal;

import java.util.List;

public class LoanModalWithoutMemberDetails {
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

    private String loanStatus;

//    public MemberModal getMemberDetails() {
//        return memberDetails;
//    }
//
//    private MemberModal memberDetails;

    public long getLoanAmountRecovered() {
        return loanAmountRecovered;
    }

    private long loanAmountRecovered;

    public long getInterestAccrued() {
        return interestAccrued;
    }

    private long interestAccrued;
}
