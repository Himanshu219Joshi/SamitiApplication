package com.example.samitiapplication.networking;

import com.example.samitiapplication.modal.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import kotlin.jvm.Synchronized;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder().setLenient().create();

    private ApiClient() {
    }

    public static synchronized Retrofit instance() {
        if (retrofit == null) {
//            AuthInterceptor authInterceptor = new AuthInterceptor();

            int timeOut = 5 * 60;
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .writeTimeout(timeOut, TimeUnit.SECONDS)
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://bc83-2409-40d4-10b3-9f44-893f-cca1-1453-cee6.ngrok-free.app")
                    .addConverterFactory((GsonConverterFactory.create()))
                    .client(okHttpClient)
                    .build();

        }

        return retrofit;
    }

}
