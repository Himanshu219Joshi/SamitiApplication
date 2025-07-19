package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.samitiapplication.databinding.ActivityMemberListBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.LastMemberDetails;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MemberActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ActivityMemberListBinding binding;
    RecyclerView recyclerView;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberListBinding.inflate(getLayoutInflater());
//        binding.progressBar.setVisibility(View.GONE);
        setContentView(R.layout.activity_member_list);

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sessionManager = new SessionManager(getApplicationContext());

        String token = sessionManager.getToken();

        Call<List<MemberModal>> call = apiInterface.getMembersInfoV2("Bearer "+token);
        call.enqueue(new Callback<List<MemberModal>>() {
            @Override
            public void onResponse(Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {

                if(!response.isSuccessful()) {
                    Toast.makeText(MemberActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<MemberModal> personList = response.body();
                System.out.println("Memeber Activity"+ personList.get(0));
                MemberAdapter MemberAdapter = new MemberAdapter(MemberActivity.this, personList);
                recyclerView.setLayoutManager(new LinearLayoutManager(MemberActivity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(MemberActivity.this, LinearLayoutManager.VERTICAL));

                recyclerView.setAdapter(MemberAdapter);
            }

            @Override
            public void onFailure(Call<List<MemberModal>> call, Throwable t) {
                Toast.makeText(MemberActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}