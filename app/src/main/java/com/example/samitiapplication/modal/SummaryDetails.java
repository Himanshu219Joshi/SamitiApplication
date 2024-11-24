package com.example.samitiapplication.modal;

public class SummaryDetails {

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public LoanDetail getLastLoan() {
        return lastLoan;
    }

    public void setLastLoan(LoanDetail lastLoan) {
        this.lastLoan = lastLoan;
    }

    private Summary summary;
    private LoanDetail lastLoan;

}
