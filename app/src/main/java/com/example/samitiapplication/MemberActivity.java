package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.samitiapplication.MemberAdapter;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MemberActivity extends AppCompatActivity implements MemberAdapter.OnMemberItemClickListener {

    RecyclerView recyclerView;
    List<MemberModal> memberList;
    MemberAdapter memberAdapter;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        // --- COLOR SYNC WITH MAIN ACTIVITY ---
        Toolbar toolbar = findViewById(R.id.memberToolbar);
        setSupportActionBar(toolbar);

        // This ensures the text and back arrow are white (common in Main Activities)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Member List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.memberRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionManager = new SessionManager(getApplicationContext());

        fetchMembers();
    }

    @Override
    public void onMemberItemClick(int position) {
        if (memberAdapter != null) {
            MemberModal clickedMember = memberAdapter.getMemberAt(position);

            // Opens the Remove/Detail Activity
            Intent intent = new Intent(MemberActivity.this, MemberRemoveActivity.class);
            intent.putExtra("memberId", clickedMember.get_id());
            startActivity(intent);
        }
    }

    // This handles the back button in the toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchMembers() {
        Retrofit instance = ApiClient.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        String token = sessionManager.getToken();
        Call<List<MemberModal>> call = apiInterface.getMembersInfoV2("Bearer " + token);

        call.enqueue(new Callback<List<MemberModal>>() {
            @Override
            public void onResponse(@NonNull Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    memberList = response.body();
                    memberAdapter = new MemberAdapter(MemberActivity.this, memberList, MemberActivity.this);
                    recyclerView.setAdapter(memberAdapter);
                } else {
                    Toast.makeText(MemberActivity.this, "Failed to load members", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MemberModal>> call, Throwable t) {
                Log.e("MemberActivity", t.getMessage());
                Toast.makeText(MemberActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loan_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search member name...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMembers(newText);
                return true;
            }
        });
        return true;
    }

    private void filterMembers(String text) {
        if (memberList == null) return;

        List<MemberModal> filteredList = new ArrayList<>();
        for (MemberModal member : memberList) {
            if (member.getMemberName() != null &&
                    member.getMemberName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(member);
            }
        }

        if (memberAdapter != null) {
            memberAdapter.setFilteredList(filteredList);
        }
    }
}