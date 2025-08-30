package com.example.samitiapplication.modal;

public class SettleLoan {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public double getPartialAmount() {
        return partialAmount;
    }

    public void setPartialAmount(double partialAmount) {
        this.partialAmount = partialAmount;
    }

    double partialAmount;

    public String getTypeOfTenure() {
        return typeOfTenure;
    }

    public void setTypeOfTenure(String typeOfTenure) {
        this.typeOfTenure = typeOfTenure;
    }

    String typeOfTenure;


}
