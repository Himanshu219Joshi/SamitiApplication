package com.example.samitiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.databinding.ActivityMainBinding;
import com.example.samitiapplication.databinding.ActivitySummaryBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.Summary;
import com.example.samitiapplication.networking.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SummaryDetails extends AppCompatActivity {

    ApiInterface apiInterface;
    ActivitySummaryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}