package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;
import com.example.samitiapplication.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserView extends AppCompatActivity {

    TextView tvInvestedAmount, tvLoanTakenAmount, tvInterestEarnedAmount, tvMemberName, tvLoanAmount, tvOutstandingAmount, tvInterestPaidAmount, tvEmiValue, tvDueDateValue, tvPercentagePaid, tvTenureValue;

    ProgressBar pbLoanProgress;

    SessionManager sessionManager;

    ApiInterface apiInterface;

    Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        tvMemberName = findViewById(R.id.tvMemberName);
        tvInvestedAmount = findViewById(R.id.tvInvestedAmount);
        tvLoanTakenAmount = findViewById(R.id.tvLoanTakenAmount);
        tvInterestEarnedAmount = findViewById(R.id.tvInterestEarnedAmount);
        tvLoanAmount = findViewById(R.id.tvLoanAmount);
        tvOutstandingAmount = findViewById(R.id.tvOutstandingAmount);
        tvInterestPaidAmount = findViewById(R.id.interestPaidAmount);
        tvEmiValue = findViewById(R.id.tvEmiValue);
        tvDueDateValue = findViewById(R.id.tvDueDateValue);
        tvTenureValue = findViewById(R.id.tvTenureValue);
        tvPercentagePaid = findViewById(R.id.tvPercentagePaid);
        pbLoanProgress = findViewById(R.id.pbLoanProgress);

        utils = new Utils();



        sessionManager = new SessionManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getToken();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");

        Call<List<MemberModal>> getMemberInfo = apiInterface.getMember(memberId,"Bearer "+token);



        getMemberInfo.enqueue(new Callback<List<MemberModal>>() {
            @Override
            public void onResponse(@NonNull Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {
                if(response.body() != null) {
                    List<MemberModal> memberDetail = response.body();
                    System.out.println("Member Name"+memberDetail.get(0).getMemberName());
                    tvMemberName.setText(String.valueOf(memberDetail.get(0).getMemberName().concat(" ").concat(memberDetail.get(0).getFatherName())));
                    tvInvestedAmount.setText(String.valueOf(memberDetail.get(0).getInvestedMoney()));
                    tvLoanTakenAmount.setText(String.valueOf(memberDetail.get(0).getLoanAmount()));
                    tvInterestEarnedAmount.setText(String.valueOf(memberDetail.get(0).getInterestEarned()));
                    tvLoanAmount.setText(String.valueOf(memberDetail.get(0).getLoanAmount()));
                    tvEmiValue.setText(String.valueOf(memberDetail.get(0).getLoanDetails().getEmiAmount()));
                    tvDueDateValue.setText(String.valueOf(utils.getNextDueDate()));
                    tvInterestPaidAmount.setText(String.valueOf(memberDetail.get(0).getLoanDetails().getInterestAccrued()));
                    tvTenureValue.setText(String.valueOf(memberDetail.get(0).getLoanDetails().getLoanTenure()));

                    String percentagePaid = String.valueOf(utils.getPercentagePaid(memberDetail.get(0).getLoanDetails().getLoanAmount(),memberDetail.get(0).getLoanDetails().getLoanAmountRecovered()));
                    String outStandingAmount = String.valueOf(utils.getOutstandingAmount(memberDetail.get(0).getLoanDetails().getLoanAmount(),memberDetail.get(0).getLoanDetails().getLoanAmountRecovered()));
                    tvPercentagePaid.setText(percentagePaid);
                    pbLoanProgress.setProgress(Integer.parseInt(percentagePaid));
                    tvOutstandingAmount.setText(String.valueOf(outStandingAmount));



                }

//                Toast.makeText(FullLoanDetails.this, "Response"+response.isSuccessful(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<MemberModal>> call, @NonNull Throwable t) {
//                loading_full_loan.setVisibility(View.GONE);
                System.out.println("Stack trace:"+t.getMessage());
//                Toast.makeText(MemberRemoveActivity.this, "Response"+t.getStackTrace(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
