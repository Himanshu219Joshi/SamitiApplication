package com.example.samitiapplication;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.emoji2.bundled.BundledEmojiCompatConfig;
import androidx.emoji2.text.EmojiCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.samitiapplication.data.DatabaseHelper;
import com.example.samitiapplication.modal.ApiInterface;
import com.example.samitiapplication.modal.members.MemberModal;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;
import com.example.samitiapplication.utils.CustomDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemFragment extends AppCompatActivity implements MonthlyPaymentAdapter.buttonClickListener {

    private RecyclerView recyclerView;
    private ArrayList<MemberModal> memberDetails = new ArrayList<>();
    private MultiAdapter adapter;

    private CustomDate customDate;
    ApiInterface apiInterface;
    DatabaseHelper db;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiple_selection);
        EmojiCompat.init(new BundledEmojiCompatConfig(this));
        AppCompatButton btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelected);
        AppCompatButton resetDatabase = findViewById(R.id.resetBtn);

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMultiSelect);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, memberDetails);
        recyclerView.setAdapter(adapter);

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        customDate = new CustomDate()

//        recyclerViewItemFragment = findViewById(R.id.list);
        ;
//        recyclerViewItemFragment.setHasFixedSize(true);
//
//        recyclerViewItemFragment.setLayoutManager(new LinearLayoutManager(this));
        sessionManager = new SessionManager(getApplicationContext());

        String token = sessionManager.getToken();

        Call<List<MemberModal>> call = apiInterface.getMembersInfoV2("Bearer " + token);
        call.enqueue(new Callback<List<MemberModal>>() {
            @Override
            public void onResponse(Call<List<MemberModal>> call, @NonNull Response<List<MemberModal>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(ItemFragment.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                Cursor cursor = db.getAllData();

                List<Integer> paidArray = new ArrayList<Integer>();

                while (cursor.moveToNext()) {
                    String installmentValue = cursor.getString(2);
                    boolean isPaid = installmentValue.equalsIgnoreCase("true") || installmentValue.equalsIgnoreCase("1");
                    if (isPaid){
                        paidArray.add(cursor.getInt(0));
                    }
                }


                memberDetails = (ArrayList<MemberModal>) response.body();

                assert memberDetails != null;

                memberDetails = (ArrayList<MemberModal>) memberDetails.stream().filter(item -> Objects.equals(item.getMemberStatus(), "Active")).collect(Collectors.toList());
                for (MemberModal memberDetailData : memberDetails) {
                    boolean isPaid = paidArray.contains(Integer.parseInt(String.valueOf(memberDetailData.getMemberId())));
                    if (isPaid) {
                        memberDetailData.setPaid(true);
                    } else {
                        memberDetailData.setPaid(false);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(ItemFragment.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(ItemFragment.this, LinearLayoutManager.VERTICAL));
                adapter = new MultiAdapter(ItemFragment.this, memberDetails);
                recyclerView.setAdapter(adapter);

//                personList = response.body();
//                MyItemRecyclerViewAdapter MemberAdapter = new MyItemRecyclerViewAdapter(ItemFragment.this, personList, ItemFragment.this );
//                recyclerViewItemFragment.setAdapter(MemberAdapter);
            }

            @Override
            public void onFailure(Call<List<MemberModal>> call, Throwable t) {
                System.out.println("Error : "+t.fillInStackTrace());
                Toast.makeText(ItemFragment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        createList();


        resetDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(getApplicationContext());
                db.resetTable();
            }
        });


        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Integer> sharinglist =new HashMap<String, Integer>();
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                if(intent.getPackage() != null){
//                    startActivity(intent);
//                } else {
                    Toast.makeText(ItemFragment.this, "Package Not Found", Toast.LENGTH_SHORT).show();
//                }
//                TextView textView = findViewById(R.id.listUse);
                if (!adapter.getSelected().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();

                    Calendar calendar = Calendar.getInstance();
                    int month = calendar.get(Calendar.MONTH);
                    String monthName = customDate.getMonthNameHindi(month);
                    List<String> fatherArray = new ArrayList<>();
//                    List<MemberModal> memberNames = adapter.getSelected().stream().filter(item -> Boolean.parseBoolean(item.getMemberName())).collect(Collectors.toList());
                    List<MemberModal> memberNames;
                    String[] meberInfo;
                    stringBuilder.append("श्री गौड़ दमावत समिति ").append(monthName).append(" 2025   \n 500/- \n\n");
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        String memberName = adapter.getSelected().get(i).getMemberName();
//                        System.out.println("Memeber Name 1"+fatherName);
                        memberNames = adapter.getSelected().stream().filter(item -> Objects.equals(item.getMemberName(), "Bhagirath")).collect(Collectors.toList());
                        System.out.println("Member Name 2"+memberNames);
                        if (memberNames.isEmpty()) {
                            sharinglist.put(memberName, 1);
                        }

//                        System.out.println("Shared List"+ sharinglist);
                        for(int j =0; j < memberNames.size(); j++){
                           String name = memberNames.get(j).getMemberName();
                           String fatherNameString = memberNames.get(j).getFatherName();
//                           System.out.println("Memeber Name 2"+name+" "+fatherNameString);

//                            for (MemberModal member : memberNames) {
//                                if (member.containsKey(member.getMemberName())) {
//                                    map.put(num, map.get(num) + 1);
//                                } else {
//                                    map.put(num, 1);
//                                }
//                            }
                           String[] mName = name.split(" ");

                           if(sharinglist.containsKey(mName[0])) {
                               sharinglist.put(mName[0], sharinglist.get(mName[0]) + 1);
                           } else {
                               sharinglist.put(mName[0], 1);
                           }

                            System.out.println("Memeber Name2"+ fatherNameString);
//                           if(mName[0] != null) {
//                           if(sharinglist.get(mName[0])) {
//                               sharinglist.put(mName[0], sharinglist.getOrDefault(mName[0], 0) + 1);
//                           } else {
//                               sharinglist.put(mName[0], 1);
//                           }

//
                           System.out.println("Memeber Name 3"+sharinglist);


                        }

                        fatherArray.add(adapter.getSelected().get(i).getMemberName());
                        stringBuilder.append(adapter.getSelected().get(i).getMemberId()).append(" ");
                        System.out.println("Father Array"+Arrays.toString(fatherArray.toArray()));
                        stringBuilder.append(adapter.getSelected().get(i).getMemberName().concat(" ").concat(adapter.getSelected().get(i).getFatherName()));
                        stringBuilder.append(adapter.getSelected().get(i).isPaid() ?" ✅ " : "");
                        stringBuilder.append("\n");
                    }

                    System.out.println("Member Names");

                    String whatsappUrl = "http://api.whatsapp.com/send?text=" + stringBuilder;

                    showMessage("Adapter Information", stringBuilder);
                    intent.setData(Uri.parse(whatsappUrl));
                    startActivity(intent);
                } else {
                    showToast("No Selection");
                }
            }
        });
    }

    private void createList() {
        for (int i = 0; i < memberDetails.size(); i++) {
            MemberModal memberDetailData = new MemberModal();
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

    public void showMessage(String title, StringBuilder message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    @Override
    public void onButtonClick(View view, int position, String text) {
        Toast.makeText(ItemFragment.this, "Item Clicked ", Toast.LENGTH_SHORT ).show();
    }
}