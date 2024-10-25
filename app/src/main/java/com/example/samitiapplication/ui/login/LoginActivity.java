package com.example.samitiapplication.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.MainActivity;
import com.example.samitiapplication.R;
import com.example.samitiapplication.RegistrationActivity;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LoginUser;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.databinding.ActivityLoginBinding;
import com.example.samitiapplication.networking.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    LoginUser loginUser;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        loginUser = new LoginUser();

        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        final EditText usernameEditText = binding.mobileNo;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button registerBtn = binding.register;

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//
//                if (loginFormState == null) {
//                    return;
//                }
//                loginButton.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                    loginUser.setPhoneNumber(usernameEditText.getText().toString());
//
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                    loginUser.setPassword(passwordEditText.getText().toString());
//                }
//            }
//        });
//
//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
//                if (loginResult.getSuccess() != null) {
////                    updateUiWithUser(loginResult.getSuccess());
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        if (usernameEditText != null) {
            usernameEditText.addTextChangedListener(afterTextChangedListener);
        }
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
                Retrofit instance = ApiClient.instance();
                loginUser.setPassword(passwordEditText.getText().toString());
                assert usernameEditText != null;
                loginUser.setMobileNumber(usernameEditText.getText().toString());
                ApiInterface apiInterface = instance.create(ApiInterface.class);
                Call<LoginUser> loginUserCall = apiInterface.signIn(loginUser);
                loginUserCall.enqueue(new Callback<LoginUser>() {
                    @Override
                    public void onResponse(Call<LoginUser> call, @NonNull Response<LoginUser> response) {
                        String tokenValue = null;
                        if(response.body() != null) {
                            Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                            tokenValue = response.body().getToken();
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if(response.body() != null) {
                            editor.putString("token", tokenValue);
                        }
                        editor.apply();
                        if(tokenValue != null) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            loadingProgressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "User is invalid", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginUser> call, Throwable t) {
                        System.out.println("Failed"+ t.getMessage());

                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }




}