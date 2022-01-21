package com.purnendu.PocketNews.BackgroundServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BackgroundService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            AlarmHelper alarmHelper = new AlarmHelper(context);
            alarmHelper.SetAlarm();
        } else {
            Intent i = new Intent(context, Services.class);
            context.startService(i);
        }
    }
}
