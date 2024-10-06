package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.databinding.ActivityListBinding;
import com.example.samitiapplication.databinding.ActivityMainBinding;
import com.example.samitiapplication.modal.ApiInterface;

import com.example.samitiapplication.modal.Person;
import com.example.samitiapplication.networking.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ActivityMainBinding binding;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final TextView textView = (TextView) findViewById(R.id.mobileNo);

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (binding.mobileNo.getText().toString().isEmpty()){
                    binding.mobileNo.setError("Mobile No Required");
                } else {
                    Toast.makeText(MainActivity.this, "Login User No: "+binding.mobileNo.getText(), Toast.LENGTH_LONG).show();
//                    Person person = new Person();
//                    person.setName(binding.name.getText().toString());
//
//                    apiInterface.insert(person).enqueue(new Callback<Person>() {
//                        @Override
//                        public void onResponse(Call<Person> call, Response<Person> response) {
//                            Toast.makeText(MainActivity.this, "Succesfully Inserted", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Person> call, Throwable t) {
//                            Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });

        binding.showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }


}