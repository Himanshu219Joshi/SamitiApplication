package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.loans.LoanModal;
import com.example.samitiapplication.modal.SettleLoan;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FullLoanDetails extends AppCompatActivity {

    SessionManager sessionManager;

    TextView memberNameValue, loanAmountValue, loanDateValue, totalInterestValue, emiAmountValue,
            interestRecoveredValue, amountRecoveredValue, firstGuarantorValue, secondGuarantorValue, loanStatusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_full_loan_details);
        Toolbar toolbar = findViewById(R.id.header_appbar);
        setSupportActionBar(toolbar);

        Button backBtn = findViewById(R.id.backBtn);
        Button settleBtn = findViewById(R.id.settleBtn);

        Intent intent = getIntent();
        String loanId = intent.getStringExtra("loanId");
        memberNameValue = findViewById(R.id.memberNameValue);
        loanAmountValue = findViewById(R.id.loanAmountValue);
        loanDateValue = findViewById(R.id.loanDateValue);
        totalInterestValue = findViewById(R.id.totalInterestValue);
        emiAmountValue = findViewById(R.id.emiAmountValue);
        amountRecoveredValue = findViewById(R.id.amountRecoveredValue);
        interestRecoveredValue = findViewById(R.id.interestRecoveredValue);
        firstGuarantorValue = findViewById(R.id.firstGuarantorValue);
        secondGuarantorValue = findViewById(R.id.secondGuarantorValue);
        loanStatusValue = findViewById(R.id.loanStatusValue);
        FrameLayout loading_full_loan = findViewById(R.id.loading_full_loan);

        sessionManager = new SessionManager(getApplicationContext());

        Retrofit instance = ApiClient.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        String token = sessionManager.getToken();

        loading_full_loan.setVisibility(View.VISIBLE);
        Call<LoanModal> getLoanInfo = apiInterface.getLoanInfo(loanId, "Bearer "+token);

        getLoanInfo.enqueue(new Callback<LoanModal>() {
            @Override
            public void onResponse(@NonNull Call<LoanModal> call, @NonNull Response<LoanModal> response) {
                if(response.body() != null) {
                    LoanModal loanDetail = response.body();
                    memberNameValue.setText(loanDetail.getMemberDetails().getMemberName().concat(" ").concat(loanDetail.getMemberDetails().getFatherName()));
                    loanAmountValue.setText(String.valueOf(loanDetail.getLoanAmount()));
                    loanDateValue.setText(String.valueOf(loanDetail.getDate()));
                    totalInterestValue.setText(String.valueOf(loanDetail.getTotalInterest()));
                    emiAmountValue.setText(String.valueOf(loanDetail.getEmiAmount()));
                    interestRecoveredValue.setText(String.valueOf(loanDetail.getInterestAccrued()));
                    amountRecoveredValue.setText(String.valueOf(loanDetail.getLoanAmountRecovered()));
                    firstGuarantorValue.setText(loanDetail.getGuarantors().get(0).getMemberName().concat(" ").concat(loanDetail.getGuarantors().get(0).getFatherName()));
                    secondGuarantorValue.setText(loanDetail.getGuarantors().get(1).getMemberName().concat(" ").concat(loanDetail.getGuarantors().get(1).getFatherName()));
                    loanStatusValue.setText(loanDetail.getLoanStatus());
                    loading_full_loan.setVisibility(View.GONE);
                }

//                Toast.makeText(FullLoanDetails.this, "Response"+response.isSuccessful(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(@NonNull Call<LoanModal> call, @NonNull Throwable t) {
                loading_full_loan.setVisibility(View.GONE);
            }
        });

        settleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SettleLoan> loanStatusUpdated  = apiInterface.settleLoan(loanId,"Bearer "+token);
                System.out.println(loanStatusUpdated);
                loanStatusUpdated.enqueue(new Callback<SettleLoan>() {
                    @Override
                    public void onResponse(@NonNull Call<SettleLoan> call, @NonNull Response<SettleLoan> response) {
                        assert response.body() != null;
                        loanStatusValue.setText(R.string.closed);
                        Toast.makeText(FullLoanDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onFailure(Call<SettleLoan> call, Throwable t) {
                        Toast.makeText(FullLoanDetails.this, "Response Failed", Toast.LENGTH_SHORT ).show();
                    }
                });


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}