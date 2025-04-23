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

    public void setToken(String token){
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }
}
