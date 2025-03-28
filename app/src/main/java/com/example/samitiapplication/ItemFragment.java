package com.example.samitiapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.R;
import com.example.samitiapplication.data.DatabaseHelper;
import com.example.samitiapplication.data.model.UserInfo;
import com.example.samitiapplication.databinding.ActivityMemberListBinding;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.MemberDetail;
import com.example.samitiapplication.networking.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemFragment extends AppCompatActivity implements MyItemRecyclerViewAdapter.buttonClickListener {


    ApiInterface apiInterface;
    ActivityMemberListBinding binding;
    RecyclerView recyclerView, recyclerViewItemFragment;

    SharedPreferences sharedPreferences;

    DatabaseHelper db;

    List<MemberDetail> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item_list);

        binding = ActivityMemberListBinding.inflate(getLayoutInflater());
//        binding.progressBar.setVisibility(View.GONE);


        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        recyclerViewItemFragment = findViewById(R.id.list);
 ;
        recyclerViewItemFragment.setHasFixedSize(true);

        recyclerViewItemFragment.setLayoutManager(new LinearLayoutManager(this));
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

                personList = response.body();
                MyItemRecyclerViewAdapter MemberAdapter = new MyItemRecyclerViewAdapter(ItemFragment.this, personList, ItemFragment.this );
                recyclerViewItemFragment.setAdapter(MemberAdapter);
            }

            @Override
            public void onFailure(Call<List<MemberDetail>> call, Throwable t) {
                Toast.makeText(ItemFragment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onButtonClick(int position) {
        db = new DatabaseHelper(this);
        UserInfo userInfo = new UserInfo();
        userInfo.setMemberName(personList.get(position).getMemberName());
        userInfo.setMemeberId(Integer.parseInt(personList.get(position).getMemberId()));
        userInfo.setInstallmentStatus("PAID");

        long result = db.insetData(userInfo);
        int p = position;
        Toast.makeText(ItemFragment.this, "Item Clicked "+ result, Toast.LENGTH_SHORT ).show();
    }
}
