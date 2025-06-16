package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LoginUser;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.databinding.ActivityLoginBinding;
import com.example.samitiapplication.networking.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    protected ActivityLoginBinding binding;
    LoginUser loginUser;
    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.header_appbar);
//        setSupportActionBar(toolbar);
        toolbar.setTitle("hr");


        loginUser = new LoginUser();

        sessionManager = new SessionManager(getApplicationContext());

        final TextInputEditText usernameEditText = binding.mobileNo;
        final TextInputEditText passwordEditText = (TextInputEditText) binding.password;
        final Button loginButton = binding.login;
        final FrameLayout loadingProgressBar = (FrameLayout) binding.loading;
        final Button registerBtn = binding.register;

        assert registerBtn != null;
        registerBtn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            Retrofit instance = ApiClient.instance();
            loginUser.setPassword(Objects.requireNonNull(passwordEditText.getText()).toString());
            assert usernameEditText != null;
            loginUser.setMobileNumber(Objects.requireNonNull(usernameEditText.getText()).toString());
            ApiInterface apiInterface = instance.create(ApiInterface.class);
            Call<LoginUser> loginUserCall = apiInterface.signIn(loginUser);
            loginUserCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<LoginUser> call, @NonNull Response<LoginUser> response) {
                    String tokenValue = null;
                    if (response.body() != null) {
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        tokenValue = response.body().getToken();
                    }
                    if (response.body() != null) {
                       sessionManager.setToken(tokenValue);
                    }
                    if (tokenValue != null) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        loadingProgressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "User is invalid", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginUser> call, @NonNull Throwable t) {
                    System.out.println("Failed" + t.getMessage());

                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = inflater.inflate(R.menu.menu_items, menu);
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }


}