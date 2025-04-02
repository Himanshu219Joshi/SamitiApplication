package com.example.samitiapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.samitiapplication.MultiAdapter;
import com.example.samitiapplication.R;
import com.example.samitiapplication.databinding.ActivityLoginBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.Employee;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.networking.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemFragment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MemberDetail> memberDetails = new ArrayList<>();
    private MultiAdapter adapter;
    private AppCompatButton btnGetSelected;

    ApiInterface apiInterface;
    RecyclerView recyclerViewItemFragment;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection);
//        this.btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelected);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMultiSelect);


        getSupportActionBar();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, memberDetails);
        recyclerView.setAdapter(adapter);

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

//        recyclerViewItemFragment = findViewById(R.id.list);
        ;
//        recyclerViewItemFragment.setHasFixedSize(true);
//
//        recyclerViewItemFragment.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", null);

        Call<List<MemberDetail>> call = apiInterface.getMembersInfo("Bearer " + token);
        call.enqueue(new Callback<List<MemberDetail>>() {
            @Override
            public void onResponse(Call<List<MemberDetail>> call, @NonNull Response<List<MemberDetail>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(com.example.samitiapplication.ItemFragment.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                    return;
                }

                memberDetails = (ArrayList<MemberDetail>) response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(ItemFragment.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(ItemFragment.this, LinearLayoutManager.VERTICAL));
                adapter = new MultiAdapter(ItemFragment.this, memberDetails);
                recyclerView.setAdapter(adapter);

//                personList = response.body();
//                MyItemRecyclerViewAdapter MemberAdapter = new MyItemRecyclerViewAdapter(ItemFragment.this, personList, ItemFragment.this );
//                recyclerViewItemFragment.setAdapter(MemberAdapter);
            }

            @Override
            public void onFailure(Call<List<MemberDetail>> call, Throwable t) {
                Toast.makeText(ItemFragment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        createList();

//        btnGetSelected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!adapter.getSelected().isEmpty()) {
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (int i = 0; i < adapter.getSelected().size(); i++) {
//                        stringBuilder.append(adapter.getSelected().get(i).getMemberName());
//                        stringBuilder.append("\n");
//
//                    }
//                    showToast(stringBuilder.toString().trim());
//                } else {
//                    showToast("No Selection");
//                }
//            }
//        });
    }

    private void createList() {
        for (int i = 0; i < memberDetails.size(); i++) {
            MemberDetail memberDetailData = new MemberDetail();
            memberDetailData.setMemberName("Employee " + (i + 1));

            // for example to show at least one selection
            if (i == 0) {
                memberDetailData.setPaid(false);
                memberDetailData.setNotPaid(false);
            }
            //

            memberDetails.add(memberDetailData);
        }
        adapter.setEmployees(memberDetails);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}