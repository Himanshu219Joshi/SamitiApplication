package com.example.samitiapplication.modal;

public class LoginUser {

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String phoneNumber;
    String password;

    public String getToken() {
        return token;
    }

    String token;
}
