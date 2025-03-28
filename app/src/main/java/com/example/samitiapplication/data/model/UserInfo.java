package com.example.samitiapplication.data.model;

public class UserInfo {
    public int getMemeberId() {
        return memeberId;
    }

    public void setMemeberId(int memeberId) {
        this.memeberId = memeberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getInstallmentStatus() {
        return installmentStatus;
    }

    public void setInstallmentStatus(String installmentStatus) {
        this.installmentStatus = installmentStatus;
    }

    private int memeberId;
    private String memberName;
    private String installmentStatus;
}
