package com.example.samitiapplication.networking;

import androidx.annotation.NonNull;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class NewAuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public NewAuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = tokenManager.getAccessToken();
        Request.Builder requestBuilder = chain.request().newBuilder();

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        return chain.proceed(requestBuilder.build());
    }
}

