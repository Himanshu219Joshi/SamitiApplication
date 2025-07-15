package com.example.samitiapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.NewLoanDetail;
import com.example.samitiapplication.networking.ApiClient;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.samitiapplication.databinding.ActivityAddNewLoanBinding;
import com.example.samitiapplication.networking.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewLoan extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddNewLoanBinding binding;

    ApiInterface apiInterface;

    String[] item = {"Himanshu", "Joshi", "Rajendra"};

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;

    ArrayAdapter arrayAdapterItem;

    SessionManager sessionManager;
    NewLoanDetail newLoanDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newLoanDetail = new NewLoanDetail();
        binding = ActivityAddNewLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        autoCompleteTextView = findViewById(R.id.memberIdAutoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.firstGuarantorTextView);
        autoCompleteTextView3 = findViewById(R.id.secondGuarantorTextView);
        TextInputEditText totalAmount = findViewById(R.id.totalAmountTextField);
        TextInputEditText loanAmount = findViewById(R.id.loanAmountTextField);
        TextInputEditText penaltyAmount = findViewById(R.id.penaltyAmountTextField);
        TextInputEditText loanDate = findViewById(R.id.loanDateTextField);
        Button submitBtn = findViewById(R.id.submitBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                int memberId = Integer.parseInt(item.split(" ")[0]);
                newLoanDetail.setMemberId(memberId);
                Toast.makeText(AddNewLoan.this, "Item :" + item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                int memberId = Integer.parseInt(item.split(" ")[0]);
                newLoanDetail.setFirstGuarantor(memberId);
                Toast.makeText(AddNewLoan.this, "Item :" + item, Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                int memberId = Integer.parseInt(item.split(" ")[0]);
                newLoanDetail.setSecondGuarantor(memberId);
                Toast.makeText(AddNewLoan.this, "Item :" + item, Toast.LENGTH_SHORT).show();
            }
        });

        sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getToken();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);


        Call<List<MemberDetail>> memberDetailCall = apiInterface.getMembersInfo("Bearer " + token);

        memberDetailCall.enqueue(new Callback<List<MemberDetail>>() {
            @Override
            public void onResponse(Call<List<MemberDetail>> call, Response<List<MemberDetail>> response) {
                if (response.isSuccessful()) {
                    List<MemberDetail> memberList = response.body();
                    List<String> newMemberList = new ArrayList<>();
                    assert memberList != null;
                    for (MemberDetail memberDetail : memberList) {
                        newMemberList.add(memberDetail.getMemberId() + " " + memberDetail.getMemberName());
                    }

                    arrayAdapterItem = new ArrayAdapter<>(AddNewLoan.this, R.layout.list_item, newMemberList);
                    autoCompleteTextView.setAdapter(arrayAdapterItem);
                    autoCompleteTextView2.setAdapter(arrayAdapterItem);
                    autoCompleteTextView3.setAdapter(arrayAdapterItem);
                }
            }

            @Override
            public void onFailure(Call<List<MemberDetail>> call, Throwable t) {
                Toast.makeText(AddNewLoan.this, "Something is wrong", Toast.LENGTH_SHORT).show();

            }

        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newLoanDetail.setLoanDate(String.valueOf(loanDate.getText()));
                    newLoanDetail.setLoanAmount(Long.parseLong(String.valueOf(loanAmount.getText())));
                    newLoanDetail.setTotalAmount(Long.parseLong(String.valueOf(totalAmount.getText())));
                    newLoanDetail.setPenaltyAmount(Long.parseLong(String.valueOf(penaltyAmount.getText())));

                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println("Loan Details");
                System.out.println(String.valueOf(loanDate.getText()));
                System.out.println(newLoanDetail.getLoanAmount());
                System.out.println(newLoanDetail.getEmiAmount());
                Call<NewLoanDetail> addNewLoan = apiInterface.addNewLoan("Bearer " + token, newLoanDetail);

                addNewLoan.enqueue(new Callback<NewLoanDetail>() {
                    @Override
                    public void onResponse(Call<NewLoanDetail> call, Response<NewLoanDetail> response) {
                        Toast.makeText(AddNewLoan.this, "Loan Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNewLoan.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<NewLoanDetail> call, Throwable t) {
                        Toast.makeText(AddNewLoan.this, "Something Wrong While Adding Loan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewLoan.this, MainActivity.class));
            }
        });
    }
}

