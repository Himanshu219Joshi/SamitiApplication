package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Inside onCreate of your first activity
    com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("broadcast")
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    android.util.Log.d("FCM", "Device subscribed to global broadcast");
                }
            });

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(iHome);
                finish();
            }
        }, 1000);
    }

}
