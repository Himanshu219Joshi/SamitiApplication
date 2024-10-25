package com.example.samitiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.samitiapplication.databinding.ActivityRegistrationBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.UserDetail;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationActivity extends AppCompatActivity {

    UserDetail userDetail;
    ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        EditText firstNameEditText = findViewById(R.id.firstName);
        EditText lastNameEditText =  findViewById(R.id.lastName);
        EditText mobileNumberEditText =  findViewById(R.id.mobileNo);
        EditText passwordEditText =  findViewById(R.id.password);;
        EditText aadhaarNumberEditText = findViewById(R.id.aadhaarNumber);
        ProgressBar loadingProgressBar = findViewById(R.id.loading);
        Button submitBtn = findViewById(R.id.submitBtn);


//        final EditText firstNameEditText = binding.firstName;
//        final EditText lastNameEditText = binding.lastName;
//        final EditText mobileNumberEditText = binding.mobileNo;
//        final EditText passwordEditText = binding.password;
//        final EditText aadhaarNumberEditText = binding.aadhaarNumber;
//        final ProgressBar loadingProgressBar = binding.loading;

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                Retrofit instance = ApiClient.instance();
                userDetail = new UserDetail();
                userDetail.setFirstName(firstNameEditText.getText().toString());
                userDetail.setLastName(lastNameEditText.getText().toString());
                userDetail.setMobileNumber(mobileNumberEditText.getText().toString());
                userDetail.setPassword(passwordEditText.getText().toString());
                userDetail.setAadhaarNumber(aadhaarNumberEditText.getText().toString());

                ApiInterface apiInterface = instance.create(ApiInterface.class);
                Call<UserDetail> userDetailCall = apiInterface.userRegistration(userDetail);

                userDetailCall.enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                        }
                        loadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        Toast.makeText(RegistrationActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}