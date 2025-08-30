package com.example.samitiapplication.modal;

import com.example.samitiapplication.modal.loans.LoanModal;
import com.example.samitiapplication.modal.members.MemberModal;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("samiti/getMembers")
    Call<List<MemberDetail>> getMembersInfo(@Header ("Authorization") String token);

    @GET("samiti/getMembers")
    Call<List<MemberModal>> getMembersInfoV2(@Header ("Authorization") String token);

    @GET("samiti/getSummary")
    Call<SummaryDetails> getSummary(@Header ("Authorization") String token);

    @POST("/signIn")
    Call<LoginUser> signIn(@Body LoginUser loginUser);

    @POST("/register")
    Call<UserDetail> userRegistration(@Body UserDetail userDetails);

    @GET("samiti/getLoans")
    Call<List<LoanModal>> getLoanDetail(@Header ("Authorization") String token);

//    Call<List<LoanModalWithoutMemberDetails>> getLoanDetailV2(@Header ("Authorization") String token);


    @GET("samiti/getLoans/{loanId}")
    Call<LoanModal> getLoanInfo(@Path("loanId") String loanId, @Header("Authorization") String token);

    @POST("/samiti/updateSummary")
    Call<NewLoanDetail> addNewLoan(@Header ("Authorization") String token, @Body NewLoanDetail newLoanDetail);

    @POST("/samiti/settleLoan/{loanId}")
    Call<SettleLoan> settleLoan(@Path("loanId") String loanId, @Header ("Authorization") String token, @Body SettleLoan requestBody);


}
