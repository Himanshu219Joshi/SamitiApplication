package com.example.samitiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samitiapplication.databinding.ActivityMainBinding;
import com.example.samitiapplication.databinding.ActivitySummaryBinding;
import com.example.samitiapplication.modal.ApiInterface;

import com.example.samitiapplication.modal.SummaryDetails;
import com.example.samitiapplication.networking.ApiClient;
import com.example.samitiapplication.networking.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Samiti_Channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQ_CODE = 11;
    ApiInterface apiInterface;
    ActivityMainBinding binding;
    SessionManager sessionManager;
    TextView totalAmount, lentAmount, balanceAmount, interestAmount, mobileNo, memberName, loanAmount, memberId, loanDate, loanEmi, guarantorNames, loanStatus, penaltyAmount;

    ActivitySummaryBinding summaryBinding;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        createNotificationChannel();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.rupees_sign_primary, null);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

        Bitmap largeIcon = bitmapDrawable.getBitmap();

        Toolbar toolbar = findViewById(R.id.header_appbar);
        setSupportActionBar(toolbar);

        Intent intentNotify = new Intent(getApplicationContext(), MainActivity.class);
        intentNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQ_CODE, intentNotify, PendingIntent.FLAG_IMMUTABLE);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(), R.drawable.rupees_sign_primary, null))).getBitmap())
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("New Loan Added")
                .setSummaryText("New Loan Take By");

        Notification.InboxStyle inboxStyle= new Notification.InboxStyle()
                .addLine("A")
                .addLine("B")
                .addLine("c")
                .addLine("D")
                .addLine("E")
                .addLine("F")
                .addLine("G")
                .addLine("H")
                .setBigContentTitle("Big Content")
                .setSummaryText("Big Content Summary");



        Notification notification;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
             notification = new Notification.Builder(this)
                     .setSmallIcon(R.drawable.rupees_sign_primary)
                    .setContentTitle("Notfication")
                    .setContentText("This is testing of notification")
                    .setChannelId(CHANNEL_ID)
                     .setStyle(inboxStyle)
                     .setContentIntent(pendingIntent)
                     .setAutoCancel(true)
                    .build();
             nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Samiti_Notification", NotificationManager.IMPORTANCE_HIGH));
        } else {
             notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.rupees_sign_primary)
                    .setContentTitle("Notfication")
                    .setContentText("This is testing of notification").setAutoCancel(true)
                     .setStyle(inboxStyle)
                     .setContentIntent(pendingIntent)
                    .build();
            }


        mobileNo = findViewById(R.id.mobileNo);
        totalAmount = findViewById(R.id.totalAmount);
        lentAmount = findViewById(R.id.lentAmount);
        balanceAmount = findViewById(R.id.balanceAmount);
        interestAmount = findViewById(R.id.interestAmount);
        penaltyAmount = findViewById(R.id.penaltyAmount);

        memberName = findViewById(R.id.memberName);
        loanAmount = findViewById(R.id.loanAmount);
        memberId = findViewById(R.id.memberId);
        loanDate = findViewById(R.id.loanDate);
        loanEmi = findViewById(R.id.loanEmi);
        guarantorNames = findViewById(R.id.guarantorNames);
        loanStatus = findViewById(R.id.loanStatus);



//        binding.notifyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                nm.notify(NOTIFICATION_ID, notification);
//            }
//        });


        sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getToken();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);



        Call<SummaryDetails> summaryDetails = apiInterface.getSummary("Bearer "+token);


        summaryDetails.enqueue(new Callback<SummaryDetails>() {
            @Override
            public void onResponse(@NonNull Call<SummaryDetails> call, @NonNull Response<SummaryDetails> response) {
                if(response.body() != null ) {
                    totalAmount.append(String.valueOf(" "+response.body().getSummary().getTotalAmount()));
                    lentAmount.append(String.valueOf(" "+response.body().getSummary().getLentAmount()));
                    balanceAmount.append(String.valueOf(" "+response.body().getSummary().getBalanceAmount()));
                    interestAmount.append(String.valueOf(" "+response.body().getSummary().getInterestAccrued()));
                    penaltyAmount.append(String.valueOf(" "+response.body().getSummary().getPenaltyAmount()));

                    memberName.append(String.valueOf(" "+response.body().getLastLoan().getMemberName()));
                    memberId.append(String.valueOf(" "+response.body().getLastLoan().getMemberId()));
                    if(response.body().getLastLoan().getLoanDetails() != null) {
                        loanAmount.append(String.valueOf(" " + response.body().getLastLoan().getLoanDetails().getLoanAmount()));
                        loanDate.append(String.valueOf(" " + response.body().getLastLoan().getLoanDetails().getDate()));
                        loanEmi.append((String.valueOf(" " + response.body().getLastLoan().getLoanDetails().getEmiAmount())));
                        guarantorNames.append(" "+ response.body().getLastLoan().getLoanDetails().getGuarantors().get(0).getMemberName().concat(", ").concat(response.body().getLastLoan().getLoanDetails().getGuarantors().get(1).getMemberName()));
                        loanStatus.append(" "+ response.body().getLastLoan().getLoanDetails().getLoanStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<SummaryDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Api Response Failed", Toast.LENGTH_SHORT).show();
            }
        });

//        binding.loginButton.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View view) {
//                if (binding.mobileNo.getText().toString().isEmpty()){
//                    binding.mobileNo.setError("Mobile No Required");
//                } else {
//                    Toast.makeText(MainActivity.this, "Login User No: "+mobileNo.getText(), Toast.LENGTH_LONG).show();
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
//                }
//            }
//        });

        binding.showSummaryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MemberActivity.class));
            }
        });

        binding.lastLoanDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoanDetailActivity.class));
            }
        });

        binding.addNewLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewLoan.class));
            }
        });

        binding.monthlyInstallment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ItemFragment.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = inflater.inflate(R.menu.menu_items, menu);
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        } else {

        }
    }

}