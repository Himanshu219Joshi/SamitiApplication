package com.example.samitiapplication.networking;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor extends AppCompatActivity implements Interceptor  {
    Context context;
    SessionManager sessionManager;

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request requestBuilder = chain.request();

        String token = sessionManager.fetchToken();
        requestBuilder.newBuilder().addHeader("Authorization", "Bearer "+token);

        return chain.proceed(requestBuilder.newBuilder().build());
    }


}
