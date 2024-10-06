package com.example.samitiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.samitiapplication.databinding.ActivityMainBinding;

public class Summary extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}