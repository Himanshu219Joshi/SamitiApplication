package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;
import com.example.samitiapplication.utils.Utils;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NewMemberView extends AppCompatActivity {

    TextView tvInvestedAmount, tvLoanTakenAmount, tvInterestEarnedAmount, tvMemberName, tvLoanAmount, tvOutstandingAmount, tvInterestPaidAmount, tvEmiValue, tvDueDateValue, tvPercentagePaid, tvTenureValue;

    MaterialCardView familyCardView;
    ProgressBar pbLoanProgress;

    SessionManager sessionManager;

    ApiInterface apiInterface;

    Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member_view);

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
        familyCardView = findViewById(R.id.familyCardView);


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

                    tvMemberName.setText(String.valueOf(memberDetail.get(0).getMemberName().concat(" ").concat(memberDetail.get(0).getFatherName())));
                    tvInvestedAmount.setText(String.valueOf("₹ "+memberDetail.get(0).getInvestedMoney()));
                    tvLoanTakenAmount.setText(String.valueOf(memberDetail.get(0).getLoanAmount()));
                    tvInterestEarnedAmount.setText(String.valueOf(memberDetail.get(0).getInterestEarned()));
                    if(memberDetail.get(0).getLoanAmount() > 0) {
                        tvLoanAmount.setText(String.valueOf(memberDetail.get(0).getLoanAmount()));
                        tvEmiValue.setText(String.valueOf(memberDetail.get(0).getLoanDetails().getEmiAmount()));
                        tvDueDateValue.setText(String.valueOf(utils.getNextDueDate()));
                        tvInterestPaidAmount.setText(String.valueOf(memberDetail.get(0).getLoanDetails().getInterestAccrued()));
                        tvTenureValue.setText(String.valueOf("Tenure: " + memberDetail.get(0).getLoanDetails().getLoanTenure()).concat(" Months"));

                        String percentagePaid = String.valueOf(utils.getPercentagePaid(memberDetail.get(0).getLoanDetails().getLoanAmount(), memberDetail.get(0).getLoanDetails().getLoanAmountRecovered()));
                        String outStandingAmount = String.valueOf(utils.getOutstandingAmount(memberDetail.get(0).getLoanDetails().getLoanAmount(), memberDetail.get(0).getLoanDetails().getLoanAmountRecovered()));

                        System.out.println("Percentage Paid: "+ percentagePaid);
                        System.out.println("Outstanding Amount: "+ outStandingAmount);
                        tvPercentagePaid.setText(percentagePaid.concat("% Paid"));
                        pbLoanProgress.setProgress(Integer.parseInt(percentagePaid));
                        tvOutstandingAmount.setText(String.valueOf(outStandingAmount));
                    }

                    boolean familyMembers = memberDetail.get(0).getFamilyMembersInfo() != null;
                    if(familyMembers) {
                        int size =  memberDetail.get(0).getFamilyMembersInfo().size();
                        int i = 0;
                        System.out.println("Size ::::" + size);
                        System.out.println("Family Info" + memberDetail.get(0).getFamilyMembersInfo().get(0).getMemberName());
                        for (i = 0; i < size; i++) {
                            System.out.println("Family Name:::::" + memberDetail.get(0).getFamilyMembersInfo().get(i).getMemberName());
                            String familyMemberName = String.valueOf(memberDetail.get(0).getFamilyMembersInfo().get(i).getMemberName().concat(" "+memberDetail.get(0).getFamilyMembersInfo().get(i).getFatherName()));
                            addFamilyMemberRow(familyMemberName, memberDetail.get(0).getFamilyMembersInfo().get(i).get_id());
                        }
                    } else {
                        familyCardView.setVisibility(View.GONE);
                    }

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

    public void addFamilyMemberRow(String name, String memberId) {
        // 1. Find the container
        LinearLayout container = findViewById(R.id.familyMemberContainer);

        // 2. Inflate the row layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View memberRow = inflater.inflate(R.layout.activity_item_family_member, container, false);

        // 3. Set the dynamic data
        TextView tvName = memberRow.findViewById(R.id.tvMemberName);
        tvName.setText(name);

        // 4. Add a click listener to the new row
        memberRow.setOnClickListener(v -> {
            // Handle click (e.g., open member details)
            Intent intent = new Intent(NewMemberView.this, NewMemberView.class);
            intent.putExtra("memberId", memberId);
            startActivity(intent);
            Toast.makeText(this, "Clicked: " + name, Toast.LENGTH_SHORT).show();
//            finish();
        });

        // 5. Add the row to the container
        container.addView(memberRow);

        // 6. Optional: Add a divider line after the row
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
//            divider.setBackgroundColor(androidx.pdf.ink.view.colorpalette.model.Color.parseColor("#EEEEEE"));
        container.addView(divider);
    }


}
