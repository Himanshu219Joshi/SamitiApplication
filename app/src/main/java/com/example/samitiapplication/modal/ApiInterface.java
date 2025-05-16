package com.example.samitiapplication.modal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryName;

public interface ApiInterface {


    @GET("samiti/getMembers")
    Call<List<MemberDetail>> getMembersInfo(@Header ("Authorization") String token);

    @GET("samiti/getSummary")
    Call<SummaryDetails> getSummary(@Header ("Authorization") String token);

    @POST("/signIn")
    Call<LoginUser> signIn(@Body LoginUser loginUser);

    @POST("/register")
    Call<UserDetail> userRegistration(@Body UserDetail userDetails);

    @GET("samiti/getLoans")
    Call<List<LoanDetail>> getLoanDetail(@Header ("Authorization") String token);

    @GET("samiti/getLoans/{loanId}")
    Call<LoanDetail> getLoanInfo(@Path("loanId") String loanId, @Header("Authorization") String token);

    @POST("/samiti/updateSummary")
    Call<NewLoanDetail> addNewLoan(@Header ("Authorization") String token, @Body NewLoanDetail newLoanDetail);
}
