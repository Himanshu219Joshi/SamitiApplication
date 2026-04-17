package com.example.samitiapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM_Service";
    private static final String CHANNEL_ID = "high_importance_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle both Notification and Data payloads
        String title = "Samiti Notification";
        String body = "You have a new update";

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }

        // Trigger notification with data payload
        sendNotification(title, body, remoteMessage.getData());
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // Auto-subscribe to broadcast topic on install/token refresh
        FirebaseMessaging.getInstance().subscribeToTopic("broadcast")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully subscribed to broadcast topic");
                    }
                });
    }

    private void sendNotification(String title, String messageBody, Map<String, String> data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Pass all data from FCM to the Activity
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.rupees_sign_primary)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_REMINDER)
                        .setContentIntent(pendingIntent)
                        // PERSISTENCE SETTINGS:
                        .setAutoCancel(false) // Does not disappear when clicked
                        .setOngoing(true)    // Cannot be swiped away easily
                        .setTimeoutAfter(43200000L); // Auto-remove after 12 hours

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "High Importance Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Used for important loan and payment reminders");
            channel.enableVibration(true);
            channel.enableLights(true);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}