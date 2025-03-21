package com.example.samitiapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.samitiapplication.databinding.ActivityAddNewLoanBinding;

public class AddNewLoan extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddNewLoanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNewLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}