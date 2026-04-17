package com.example.samitiapplication.networking;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("useDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void setRole(String role) {
        editor.putString("role", role);
        editor.commit();
    }

    public String getRole() {
        String value = sharedPreferences.getString("role", "member");
        return value;
    }

    public boolean isAdmin() {
        return getRole().equalsIgnoreCase("admin");
    }


    public void setToken(String token){
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void sessionLogOut() {
        editor.clear();
        editor.apply();
        editor.commit();
    }
}
