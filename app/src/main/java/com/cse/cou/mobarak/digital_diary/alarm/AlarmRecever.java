package com.cse.cou.mobarak.digital_diary.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cse.cou.mobarak.digital_diary.MyNotificationReceive;

/**
 * Created by mobarak on 7/17/2018.
 */

public class AlarmRecever extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        Intent intent1 = new Intent(context.getApplicationContext(), MyNotificationReceive.class);
        String message=intent.getStringExtra("message");
        String title=intent.getStringExtra("title");
        intent1.putExtra("title", title);
        intent1.putExtra("message", message);
        context.startActivity(intent1);
    }
}
