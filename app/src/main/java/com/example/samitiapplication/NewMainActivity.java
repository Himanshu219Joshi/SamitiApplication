package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LastLoanDetails;
import com.example.samitiapplication.modal.Summary;
import com.example.samitiapplication.modal.SummaryDetails;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewMainActivity extends AppCompatActivity {

    TextView totalAmount, lentAmount, balanceAmount, interestAmount, memberName, loanAmount, memberId, loanDate, loanEmi, guarantorNames, loanStatus, penaltyAmount, recoveredAmount;


    LinearLayout summaryViewDetails;
    LinearLayout lastLoanViewDetails;

    SessionManager sessionManager;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main_screen);

        // summary loan field
        totalAmount = findViewById(R.id.totalAmount);
        lentAmount = findViewById(R.id.lentAmount);
        balanceAmount = findViewById(R.id.balanceAmount);
        interestAmount = findViewById(R.id.interestAmount);
        penaltyAmount = findViewById(R.id.penaltyAmount);
        recoveredAmount = findViewById(R.id.recoveredAmount);

        // last loan fields
        memberName = findViewById(R.id.memberName);
        loanAmount = findViewById(R.id.loanAmount);
        memberId = findViewById(R.id.memberId);
        loanDate = findViewById(R.id.loanDate);
        loanEmi = findViewById(R.id.loanEmi);
        guarantorNames = findViewById(R.id.guarantorNames);
//        loanStatus = findViewById(R.id.loanStatus);

        summaryViewDetails = findViewById(R.id.summaryViewDetails);
        lastLoanViewDetails = findViewById(R.id.lastLoanViewDetails);


        sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getToken();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        Call<com.example.samitiapplication.modal.SummaryDetails> summaryDetails = apiInterface.getSummary("Bearer "+token);


        summaryDetails.enqueue(new Callback<com.example.samitiapplication.modal.SummaryDetails>() {
            @Override
            public void onResponse(@NonNull Call<com.example.samitiapplication.modal.SummaryDetails> call, @NonNull Response<com.example.samitiapplication.modal.SummaryDetails> response) {
                if(response.body() != null ) {
                    Summary summaryDetails = response.body().getSummary();
                    System.out.println("Response BOdy::::"+response.body());
                    totalAmount.setText(String.valueOf(response.body().getSummary().getTotalAmount()));
                    lentAmount.setText(String.valueOf(summaryDetails.getLentAmount()));
                    balanceAmount.setText(String.valueOf(summaryDetails.getBalanceAmount()));
                    interestAmount.setText(String.valueOf(summaryDetails.getInterestAccrued()));
                    penaltyAmount.setText(String.valueOf(summaryDetails.getPenaltyAmount()));
                    recoveredAmount.setText(String.valueOf(summaryDetails.getLoanAmountRecovered()));

                    LastLoanDetails lastLoanDetails = response.body().getLastLoanDetails();
                    memberName.setText(String.valueOf(lastLoanDetails.getMemberName()));
                    memberId.setText(String.valueOf(lastLoanDetails.getMemberId()));
                    Log.d("RAW DATA", lastLoanDetails.getLoanDetails().toString());
                    if(lastLoanDetails.getLoanDetails() != null) {
                        loanAmount.setText(String.valueOf(lastLoanDetails.getLoanDetails().getLoanAmount()));
                        loanDate.setText(String.valueOf(lastLoanDetails.getLoanDetails().getDate()));
                        loanEmi.setText((String.valueOf(lastLoanDetails.getLoanDetails().getEmiAmount())));
                        guarantorNames.setText(lastLoanDetails.getLoanDetails().getGuarantors().get(0).getMemberName().concat(", ").concat(lastLoanDetails.getLoanDetails().getGuarantors().get(1).getMemberName()));
//                        loanStatus.setText(" "+ lastLoanDetails.getLoanDetails().getLoanStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<SummaryDetails> call, Throwable t) {
                Toast.makeText(NewMainActivity.this, "Api Response Failed", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });

        summaryViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewMainActivity.this, NewMemberActivity.class));
            }
        });

        lastLoanViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewMainActivity.this, LoanDetailActivity.class));
            }
        });
    }
}
