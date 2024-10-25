package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.samitiapplication.databinding.ActivityListBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.networking.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ActivityListBinding binding;
    RecyclerView recyclerView;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
//        binding.progressBar.setVisibility(View.GONE);
        setContentView(R.layout.activity_list);

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", null);

        Call<List<MemberDetail>> call = apiInterface.getPerson("Bearer "+token);
        call.enqueue(new Callback<List<MemberDetail>>() {
            @Override
            public void onResponse(Call<List<MemberDetail>> call, @NonNull Response<List<MemberDetail>> response) {

                if(!response.isSuccessful()) {
                    Toast.makeText(ListActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<MemberDetail> personList = response.body();
                PersonAdapter PersonAdapter = new PersonAdapter(ListActivity.this, personList);
                recyclerView.setAdapter(PersonAdapter);
            }

            @Override
            public void onFailure(Call<List<MemberDetail>> call, Throwable t) {
                Toast.makeText(ListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}