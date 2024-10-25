package com.example.samitiapplication.modal;

public class LoginUser {

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String mobileNumber;
    String password;

    public String getToken() {
        return token;
    }

    String token;
}
