package com.example.samitiapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.samitiapplication.modal.AddPenaltyDetail;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPenalty extends DialogFragment {

    private ApiInterface apiInterface;
    private AutoCompleteTextView autoCompleteTextView;
    private SessionManager sessionManager;
    private EditText etPenaltyDate, etPenaltyAmount, etPenaltyDays;
    private Button btnAdd, btnCancel;

    private AddPenaltyDetail addPenaltyDetail;
    // Use onCreateView instead of onCreate for Fragments
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_add_penalty, container, false);

        // Initialize Views using the 'view' object
        autoCompleteTextView = view.findViewById(R.id.memberIdDropdown);
        etPenaltyDate = view.findViewById(R.id.penaltyDateField);
        etPenaltyAmount = view.findViewById(R.id.penaltyAmountField);
        etPenaltyDays = view.findViewById(R.id.penaltyDays);
        btnAdd = view.findViewById(R.id.btnAddPenalty);
        btnCancel = view.findViewById(R.id.btnCancel);

        sessionManager = new SessionManager(requireContext());
        apiInterface = ApiClient.instance().create(ApiInterface.class);

        setupMemberDropdown();
        setupDatePicker();

        btnCancel.setOnClickListener(v -> dismiss()); // Close the dialog

        addPenaltyDetail = new AddPenaltyDetail();

        btnAdd.setOnClickListener(v -> {
            // Add your logic to save penalty her
            addPenaltyDetail.setMemberId(Integer.parseInt(String.valueOf(autoCompleteTextView.getText()).split(" ")[0]));
            addPenaltyDetail.setPenaltyAmount(Double.parseDouble(String.valueOf(etPenaltyAmount.getText())));
            addPenaltyDetail.setPenaltyDate(String.valueOf(etPenaltyDate.getText()));
            addPenaltyDetail.setNumberOfDays(Integer.parseInt(String.valueOf(etPenaltyDays.getText())));

            System.out.println("Penalty Info"+ addPenaltyDetail);


            String token = sessionManager.getToken();
            Call<AddPenaltyDetail> call = apiInterface.addPenaltyInfo(addPenaltyDetail.getMemberId(),"Bearer " + token, addPenaltyDetail);


            call.enqueue(new Callback<AddPenaltyDetail>() {
                @Override
                public void onResponse(Call<AddPenaltyDetail> call, Response<AddPenaltyDetail> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddPenaltyDetail> call, Throwable t) {
                    Toast.makeText(requireContext(), "Failed to load members", Toast.LENGTH_SHORT).show();
                }
            });


            System.out.println("Penalty MemberId "+ addPenaltyDetail.getMemberId());
            System.out.println("Penalty Amount "+addPenaltyDetail.getPenaltyAmount());
            System.out.println("Penalty Date "+addPenaltyDetail.getDate());
            dismiss();
        });

        return view;
    }

    private void setupMemberDropdown() {

        String token = sessionManager.getToken();
        Call<List<MemberModal>> call = apiInterface.getMembersInfoV2("Bearer " + token);


        call.enqueue(new Callback<List<MemberModal>>() {
            @Override
            public void onResponse(@NonNull Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {
                if (response.isSuccessful() && isAdded()) { // isAdded() ensures fragment is still attached
                    List<MemberModal> memberList = response.body();
                    List<String> names = new ArrayList<>();
                    if (memberList != null) {
                        for (MemberModal m : memberList) {
                            names.add(m.getMemberId() + " " + m.getMemberName());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, names);
                    autoCompleteTextView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModal>> call, Throwable t) {
                if (isAdded()) Toast.makeText(requireContext(), "Failed to load members", Toast.LENGTH_SHORT).show();
            }
        });

        // Make dropdown show immediately on click
        autoCompleteTextView.setThreshold(0);
        autoCompleteTextView.setOnClickListener(v -> autoCompleteTextView.showDropDown());
    }

    private void setupDatePicker() {
        etPenaltyDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view, year, month, day) -> {
                etPenaltyDate.setText(day + "/" + (month + 1) + "/" + year);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    // Optional: Make the dialog wider
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}