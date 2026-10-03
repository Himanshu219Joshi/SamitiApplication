package com.example.samitiapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.loans.LoanModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewAllLoans extends AppCompatActivity implements ViewAllLoansAdapter.OnLoanItemClickListener {


    SessionManager sessionManager;
    RecyclerView recyclerView;
    List<LoanModal> loanDetail;
    ViewAllLoansAdapter viewAllLoansAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_loans);

        // --- TOOLBAR SETUP (Crucial for showing the icon) ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Loan Details");
        }

        Intent intentData = getIntent();
        String memberDetails = intentData.getStringExtra("memberDetails");
        System.out.println("Member Id in View All Loans:::::"+memberDetails);

        recyclerView = findViewById(R.id.viewAllLoanRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAllLoans.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(ViewAllLoans.this, LinearLayoutManager.VERTICAL));

        sessionManager = new SessionManager(getApplicationContext());

        Retrofit instance = ApiClient.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        String token = sessionManager.getToken();
        Call<List<LoanModal>> loanDetailCall = apiInterface.getMemberLoans("Bearer " + token, memberDetails);

        loanDetailCall.enqueue(new Callback<List<LoanModal>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoanModal>> call, @NonNull Response<List<LoanModal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loanDetail = response.body();
                    System.out.println("Loan Details: " + loanDetail.toString());
                    viewAllLoansAdapter = new ViewAllLoansAdapter(ViewAllLoans.this, loanDetail, ViewAllLoans.this);
                    recyclerView.setAdapter(viewAllLoansAdapter);
                } else {
                    Toast.makeText(ViewAllLoans.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoanModal>> call, Throwable t) {
                Log.d("Loan Details Activity", t.getMessage());
                Toast.makeText(ViewAllLoans.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loan_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // --- WHITE SEARCH STYLING ---
        @SuppressLint("RestrictedApi") SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (searchAutoComplete != null) {
            searchAutoComplete.setTextColor(android.graphics.Color.WHITE);
            searchAutoComplete.setHintTextColor(android.graphics.Color.LTGRAY);
            // Make cursor white
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                searchAutoComplete.setTextCursorDrawable(null);
            }
        }

        // Make search icon (magnifying glass) white
        android.widget.ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        if (searchIcon != null) searchIcon.setColorFilter(android.graphics.Color.WHITE);

        searchView.setQueryHint("Search member name...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        return true;
    }

    private void filterList(String text) {
        if (loanDetail == null) return;

        List<LoanModal> filteredList = new ArrayList<>();
        for (LoanModal loan : loanDetail) {
            // Check inside getMemberDetails() for the name
            if (loan.getMemberDetails() != null && loan.getMemberDetails().getMemberName() != null) {
                if (loan.getMemberDetails().getMemberName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(loan);
                }
            }
        }

        if (viewAllLoansAdapter != null) {
            viewAllLoansAdapter.setFilteredList(filteredList);
        }
    }

    @Override
    public void onLoanItemClick(int position) {
        // Get the loan object from the adapter
        LoanModal clickedLoan = viewAllLoansAdapter.getLoanAt(position);

        if (clickedLoan != null) {
            Intent intent = new Intent(this, FullLoanDetails.class);
            // Use the correct ID field from your Modal (usually get_id() or getLoanId())
            intent.putExtra("loanId", clickedLoan.get_id());
            startActivity(intent);
        }
    }
}
