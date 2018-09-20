package com.cse.cou.mobarak.digital_diary;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.TextView;

public class MyNotificationReceive extends AppCompatActivity {
    Ringtone r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification_receive);

        setTitle("Remainder Alarm");

        final TextView title= (TextView) findViewById(R.id.title_of_alarm);
        TextView message= (TextView) findViewById(R.id.message_of_alarm);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         r = RingtoneManager.getRingtone(this, notification);
        r.play();

//        AlertDialog.Builder builder=new AlertDialog.Builder(this)
//                .setTitle("Alarm Notification")
//                .setMessage(getIntent().getStringExtra("message"))
//                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        r.stop();
//                    }
//                });
//        builder.show();

        //TODO alart dialog touch event to off the alarm.


        title.setText(getIntent().getStringExtra("title"));
        message.setText(getIntent().getStringExtra("message"));
//
//        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.activity_my_notification_receive);
//        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                r.stop();
//                finish();
//                startActivity(new Intent(MyNotificationReceive.this, RemainderListActivity.class));
//                return true;
//            }
//        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        r.stop();
        finish();
        return true;
    }
}
