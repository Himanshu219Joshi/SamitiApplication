package com.example.samitiapplication.modal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {


    @GET("samiti/memberList")
    Call<List<Person>> getPerson(@Header ("Authorization") String token);

    @GET("samiti/getSummary")
    Call<Summary> getSummary(@Header ("Authorization") String token);

    @POST("/signIn")
    Call<LoginUser> signIn(@Body LoginUser loginUser);
}
