package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.RemoveMember;
import com.example.samitiapplication.modal.loans.LoanModal;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MemberRemoveActivity extends AppCompatActivity {


    SessionManager sessionManager;
    TextView memberName, investedMoney, loanAmount;
    
    RemoveMember removeMember;
    Button removeBtn;
    ApiInterface apiInterface;

    Retrofit instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member_remove);

        Toolbar toolbar = findViewById(R.id.header_appbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());


       memberName = findViewById(R.id.memberNameValue);
       investedMoney = findViewById(R.id.investedAmountValue);
       loanAmount = findViewById(R.id.loanAmountValue);
       removeBtn = findViewById(R.id.removeMemberBtn);

       removeMember = new RemoveMember();

        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");
        instance = ApiClient.instance();

        String token = sessionManager.getToken();
        apiInterface = instance.create(ApiInterface.class);
        Call<List<MemberModal>> getMemberInfo = apiInterface.getMember(memberId,"Bearer "+token);

        getMemberInfo.enqueue(new Callback<List<MemberModal>>() {
            @Override
            public void onResponse(@NonNull Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {
                if(response.body() != null) {
                    List<MemberModal> memberDetail = response.body();
                    System.out.println("Member Name"+memberDetail.get(0).getMemberName());
                    memberName.setText(memberDetail.get(0).getMemberName());
                    investedMoney.setText(String.valueOf(memberDetail.get(0).getInvestedMoney()));
                    loanAmount.setText(String.valueOf(memberDetail.get(0).getLoanAmount()));
                    removeMember.setAmount(memberDetail.get(0).getInvestedMoney());
                    
                }

//                Toast.makeText(FullLoanDetails.this, "Response"+response.isSuccessful(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<MemberModal>> call, @NonNull Throwable t) {
//                loading_full_loan.setVisibility(View.GONE);
                System.out.println("Stack trace:"+t.getMessage());
                Toast.makeText(MemberRemoveActivity.this, "Response"+t.getStackTrace(), Toast.LENGTH_SHORT).show();
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MemberModal> removedMemeber = apiInterface.removeMember(memberId,"Bearer "+token, removeMember);
                
                removedMemeber.enqueue(new Callback<MemberModal>() {
                    @Override
                    public void onResponse(Call<MemberModal> call, Response<MemberModal> response) {
                        Toast.makeText(MemberRemoveActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MemberModal> call, Throwable t) {
                        System.out.println("Failed Message"+t.getMessage());
                        Toast.makeText(MemberRemoveActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}