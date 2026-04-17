package com.example.samitiapplication.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionManager {
    public static boolean isAdmin(Context context) {
        SharedPreferences pref = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String role = pref.getString("role", "member");
        return role.equalsIgnoreCase("admin");
    }
}
