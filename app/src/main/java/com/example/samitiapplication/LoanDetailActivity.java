package com.example.samitiapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LoanDetail;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoanDetailActivity extends AppCompatActivity {

    SessionManager sessionManager;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_detail_list);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        TextView member_name = findViewById(R.id.memberName);
        TextView member_id = findViewById(R.id.memberId);
        TextView loan_amount = findViewById(R.id.loanAmount);
        TextView loan_date = findViewById(R.id.loanDate);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LoanDetailActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(LoanDetailActivity.this, LinearLayoutManager.VERTICAL));

        sessionManager = new SessionManager(getApplicationContext());

        Retrofit instance = ApiClient.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        String token = sessionManager.getToken();
        Call<List<LoanDetail>> loanDetailCall = apiInterface.getLoanDetail("Bearer "+token);

        loanDetailCall.enqueue(new Callback<List<LoanDetail>>() {
            @Override
            public void onResponse(Call<List<LoanDetail>> call, Response<List<LoanDetail>> response) {
//                if(response.isSuccessful() && response.body() != null) {
//                    member_name.append(response.body().getMemberName());
//                    member_id.append(response.body().getMemberId());
//                    loan_amount.append(response.body().getLoanAmount());
//                    loan_date.append((response.body().getDate()));
//                }
                System.out.println("Information"+ response.body());
                if(!response.isSuccessful()) {
                    Toast.makeText(LoanDetailActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<LoanDetail> loanDetail = response.body();
                LoanDetailsAdapter loanDetailsAdapter = new LoanDetailsAdapter(LoanDetailActivity.this, loanDetail);
                recyclerView.setAdapter(loanDetailsAdapter);


            }

            @Override
            public void onFailure(Call<List<LoanDetail>> call, Throwable t) {
                Toast.makeText(LoanDetailActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}