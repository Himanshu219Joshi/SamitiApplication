package com.example.samitiapplication.networking;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;



public class SessionManager extends AppCompatActivity {

    SharedPreferences preference = getSharedPreferences("preference", Context.MODE_PRIVATE);

    private String USER_TOKEN = "token";

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchToken() {
        return preference.getString(USER_TOKEN, null);
    }
}
