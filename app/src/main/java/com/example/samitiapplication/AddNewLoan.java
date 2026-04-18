package com.example.samitiapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.samitiapplication.data.DatabaseHelper;
import com.example.samitiapplication.databinding.ActivityAppLayoutBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LastMemberDetails;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.NewLoanDetail;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.samitiapplication.databinding.ActivityAddNewLoanBinding;
import com.example.samitiapplication.networking.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewLoan extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAppLayoutBinding binding;

//    private ActivityAddNewLoanBinding binding;



    ApiInterface apiInterface;

    String[] item = {"Himanshu", "Joshi", "Rajendra"};

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;

    ArrayAdapter arrayAdapterItem;

    SessionManager sessionManager;
    NewLoanDetail newLoanDetail;

    DatabaseHelper databaseHelper;

    FloatingActionButton fabAddPenalty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newLoanDetail = new NewLoanDetail();
        binding = ActivityAppLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fabAddPenalty = findViewById(R.id.fabAddPenalty);


        autoCompleteTextView = findViewById(R.id.memberIdAutoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.firstGuarantorTextView);
        autoCompleteTextView3 = findViewById(R.id.secondGuarantorTextView);
        TextInputEditText totalAmount = findViewById(R.id.totalAmountTextField);
        TextInputEditText loanAmount = findViewById(R.id.loanAmountTextField);
        TextInputEditText penaltyAmount = findViewById(R.id.penaltyAmountTextField);
        TextInputEditText loanDate = findViewById(R.id.loanDateTextField);
        Button submitBtn = findViewById(R.id.submitBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);

        fabAddPenalty.setOnClickListener(v -> {
            AddPenalty dialog = new AddPenalty();
            dialog.show(getSupportFragmentManager(), "AddPenaltyDialog");
        });

//        if (fabAddPenalty != null) {
//            fabAddPenalty.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showPenaltyDialog();
//                }
//            });
//        } else {
//            // This log will tell you if the ID is still not being found
//            Log.e("AddNewLoan", "FAB not found in layout!");
//        }

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

        databaseHelper = new DatabaseHelper(getApplicationContext());

        Cursor cursor = databaseHelper.getAllData();
        long totalAmountValue = 0;
        while (cursor.moveToNext()) {
            String installmentValue = cursor.getString(2);
            boolean isPaid = installmentValue.equalsIgnoreCase("true") || installmentValue.equalsIgnoreCase("1");
            if (isPaid){
                totalAmountValue += Long.parseLong(cursor.getString(3));
            }
        }

        System.out.println("Total Amount: "+ totalAmountValue);

        totalAmount.setText(String.valueOf(totalAmountValue));

        Call<List<MemberModal>> memberDetailCall = apiInterface.getMembersInfoV2("Bearer " + token);

        memberDetailCall.enqueue(new Callback<List<MemberModal>>() {

            @Override
            public void onResponse(@NonNull Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {
                if (response.isSuccessful()) {
                    List<MemberModal> memberList = response.body();
                    List<String> newMemberList = new ArrayList<>();
                    assert memberList != null;
                    for (MemberModal memberDetail : memberList) {
                         newMemberList.add(memberDetail.getMemberId() + " " + memberDetail.getMemberName());
                    }

                    arrayAdapterItem = new ArrayAdapter<>(AddNewLoan.this, R.layout.list_item, newMemberList);
                    autoCompleteTextView.setAdapter(arrayAdapterItem);
                    autoCompleteTextView2.setAdapter(arrayAdapterItem);
                    autoCompleteTextView3.setAdapter(arrayAdapterItem);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModal>> call, Throwable t) {
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

    private void showPenaltyDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_penalty, null);


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());

        // Initialize Dialog Views
//        Spinner spinnerMember = view.findViewById(R.id.spinnerMember);
//        EditText etAmount = view.findViewById(R.id.etPenaltyAmount);
//        EditText etDate = view.findViewById(R.id.etPenaltyDate);
//        Button btnAdd = view.findViewById(R.id.btnAddPenalty);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

//        // 1. Setup Spinner (Assuming you have a list of member names)
//        List<String> memberNames = new ArrayList<>(); // Get this from your API/Database
//        memberNames.add("Select Member");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, memberNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerMember.setAdapter(adapter);
//
//        // 2. Setup DatePicker
//        etDate.setOnClickListener(v -> {
//            Calendar c = Calendar.getInstance();
//            new DatePickerDialog(this, (view1, year, month, day) -> {
//                etDate.setText(day + "/" + (month + 1) + "/" + year);
//            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
//        });

        // 3. Add Button Logic
//        btnAdd.setOnClickListener(v -> {
//            String amount = etAmount.getText().toString();
//            String date = etDate.getText().toString();
//            String selectedMember = spinnerMember.getSelectedItem().toString();
//
//            if (amount.isEmpty() || date.isEmpty() || selectedMember.equals("Select Member")) {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//            } else {
//                // CALL YOUR API HERE to save penalty
////                savePenaltyToApi(selectedMember, amount, date);
//                dialog.dismiss();
//            }
//        });
//
        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }
}

