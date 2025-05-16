package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LoanDetail;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FullLoanDetails extends AppCompatActivity {

    SessionManager sessionManager;

    TextView memberNameValue, loanAmountValue, loanDateValue, totalInterestValue, emiAmountValue,
            interestRecoveredValue, amountRecoveredValue, firstGuarantorValue, secondGuarantorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_full_loan_details);
        Toolbar toolbar = findViewById(R.id.header_appbar);
        setSupportActionBar(toolbar);

        Button backBtn = findViewById(R.id.backBtn);

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


        sessionManager = new SessionManager(getApplicationContext());

        Retrofit instance = ApiClient.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        String token = sessionManager.getToken();
        Call<LoanDetail> getLoanInnfo = apiInterface.getLoanInfo(loanId, "Bearer "+token);

        getLoanInnfo.enqueue(new Callback<LoanDetail>() {
            @Override
            public void onResponse(Call<LoanDetail> call, Response<LoanDetail> response) {
                if(response.body() != null) {
                    LoanDetail loanDetail = response.body();
                    memberNameValue.setText(loanDetail.getMemberDetails().getMemberName().concat(" ").concat(loanDetail.getMemberDetails().getFatherName()));
                    loanAmountValue.setText(loanDetail.getLoanAmount());
                    loanDateValue.setText(String.valueOf(loanDetail.getDate()));
                    totalInterestValue.setText(String.valueOf(loanDetail.getTotalInterest()));
                    emiAmountValue.setText(String.valueOf(loanDetail.getEmiAmount()));
                    interestRecoveredValue.setText(String.valueOf(loanDetail.getInterestAccrued()));
                    amountRecoveredValue.setText(String.valueOf(loanDetail.getLoanAmountRecovered()));
                    firstGuarantorValue.setText(loanDetail.getGuarantors().get(0).getMemberName().concat(" ").concat(loanDetail.getGuarantors().get(0).getFatherName()));
                    secondGuarantorValue.setText(loanDetail.getGuarantors().get(1).getMemberName().concat(" ").concat(loanDetail.getGuarantors().get(1).getFatherName()));
                }

                Toast.makeText(FullLoanDetails.this, "Response"+response.isSuccessful(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<LoanDetail> call, Throwable t) {

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