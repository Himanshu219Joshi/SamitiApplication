package com.example.samitiapplication.modal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {


    @GET("samiti/memberList")
    Call<List<MemberDetail>> getPerson(@Header ("Authorization") String token);

    @GET("samiti/getSummary")
    Call<SummaryDetails> getSummary(@Header ("Authorization") String token);

    @POST("/signIn")
    Call<LoginUser> signIn(@Body LoginUser loginUser);

    @POST("/register")
    Call<UserDetail> userRegistration(@Body UserDetail userDetails);

    @GET("samiti/loanDetails")
    Call<List<LoanDetail>> getLoanDetail(@Header ("Authorization") String token);
}
