package com.purnendu.PocketNews;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHelper {
    Context context;

    public AlarmHelper(Context context) {
        this.context = context;
    }
    public   void SetAlarm()
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BackgroundService.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if(alarmManager!=null)
        {
            alarmManager.setRepeating(AlarmManager.RTC, ((System.currentTimeMillis())),AlarmManager.INTERVAL_HALF_DAY,pendingIntent);
        }

    }
}
