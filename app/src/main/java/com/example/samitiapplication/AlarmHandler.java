package com.example.samitiapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Executable;
import java.util.concurrent.ExecutorService;

public class AlarmHandler {
    private Context context;

    public AlarmHandler(Context context){
        this.context = context;
    }

    public void setAlarmManager() {
        Intent intent = new Intent(context, ExecutorService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(am != null ){
            long triggerAfter = 60 * 60 * 1000;
            long triggerEvery = 60 * 60 * 1000;
            am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAfter, triggerEvery, pendingIntent);
        }

    }
}
