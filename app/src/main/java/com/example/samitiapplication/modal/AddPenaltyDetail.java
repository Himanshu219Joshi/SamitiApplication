package com.example.samitiapplication.modal;

public class AddPenaltyDetail {

    public double getPenaltyAmount() {
        return PenaltyAmount;
    }

    public void setPenaltyAmount(double penaltyAmount) {
        PenaltyAmount = penaltyAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private double PenaltyAmount;
    private String memberId;

    private String date;
}
