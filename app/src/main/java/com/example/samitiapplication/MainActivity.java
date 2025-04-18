package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.databinding.ActivityMainBinding;
import com.example.samitiapplication.databinding.ActivitySummaryBinding;
import com.example.samitiapplication.modal.ApiInterface;

import com.example.samitiapplication.modal.SummaryDetails;
import com.example.samitiapplication.networking.ApiClient;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    TextView totalAmount, lentAmount, balanceAmount, interestAmount, mobileNo, memberName, loanAmount, memberId, loanDate, loanEmi;

    ActivitySummaryBinding summaryBinding;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.header_appbar);
        setSupportActionBar(toolbar);

        mobileNo = findViewById(R.id.mobileNo);
        totalAmount = findViewById(R.id.totalAmount);
        lentAmount = findViewById(R.id.lentAmount);
        balanceAmount = findViewById(R.id.balanceAmount);
        interestAmount = findViewById(R.id.interestAmount);

        memberName = findViewById(R.id.memberName);
        loanAmount = findViewById(R.id.loanAmount);
        memberId = findViewById(R.id.memberId);
        loanDate = findViewById(R.id.loanDate);
        loanEmi = findViewById(R.id.loanEmi);


        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        String token = sharedPreferences.getString("token", null);

        Call<SummaryDetails> summaryDetails = apiInterface.getSummary("Bearer "+token);


        summaryDetails.enqueue(new Callback<SummaryDetails>() {
            @Override
            public void onResponse(@NonNull Call<SummaryDetails> call, @NonNull Response<SummaryDetails> response) {
                if(response.body() != null ) {
                    totalAmount.append(String.valueOf(" "+response.body().getSummary().getTotalAmount()));
                    lentAmount.append(String.valueOf(" "+response.body().getSummary().getLentAmount()));
                    balanceAmount.append(String.valueOf(" "+response.body().getSummary().getBalanceAmount()));
                    interestAmount.append(String.valueOf(" "+response.body().getSummary().getInterestAccrued()));


                    memberName.append(String.valueOf(" "+response.body().getLastLoan().getMemberName()));
                    memberId.append(String.valueOf(" "+response.body().getLastLoan().getMemberId()));
                    if(response.body().getLastLoan().getLoanDetails() != null) {
                        loanAmount.append(String.valueOf(" " + response.body().getLastLoan().getLoanDetails().getLoanAmount()));
                        loanDate.append(String.valueOf(" " + response.body().getLastLoan().getLoanDetails().getDate()));
                        loanEmi.append((String.valueOf(" " + response.body().getLastLoan().getLoanDetails().getEmiAmount())));
                    }
                }
            }

            @Override
            public void onFailure(Call<SummaryDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Api Response Failed", Toast.LENGTH_SHORT).show();
            }
        });

//        binding.loginButton.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View view) {
//                if (binding.mobileNo.getText().toString().isEmpty()){
//                    binding.mobileNo.setError("Mobile No Required");
//                } else {
//                    Toast.makeText(MainActivity.this, "Login User No: "+mobileNo.getText(), Toast.LENGTH_LONG).show();
//                    Person person = new Person();
//                    person.setName(binding.name.getText().toString());
//
//                    apiInterface.insert(person).enqueue(new Callback<Person>() {
//                        @Override
//                        public void onResponse(Call<Person> call, Response<Person> response) {
//                            Toast.makeText(MainActivity.this, "Succesfully Inserted", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Person> call, Throwable t) {
//                            Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });

        binding.showSummaryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });

        binding.lastLoanDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoanDetailActivity.class));
            }
        });

        binding.addNewLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewLoan.class));
            }
        });

        binding.samitiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ItemFragment.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = inflater.inflate(R.menu.menu_items, menu);
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }


}