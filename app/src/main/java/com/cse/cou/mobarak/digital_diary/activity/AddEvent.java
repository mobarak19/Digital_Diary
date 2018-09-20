package com.cse.cou.mobarak.digital_diary.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.alarm.GetDifferenceOfTime;
import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.alarm.AlarmRecever;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.EventDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity {
    Calendar myCalendar1;
    EditText title, message;
    CheckBox checkBox;
    EditText date, time;
    String id;
    boolean update;
    EventDatabase eventDatabase;
    Button saveRemaindder_btn;
    String str_current = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle("Add Event");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        date = (EditText) findViewById(R.id.get_event_date);

        time = (EditText) findViewById(R.id.get_event_time);

        title = (EditText) findViewById(R.id.get_event_title);

        message = (EditText) findViewById(R.id.get_event_message);

        eventDatabase = new EventDatabase(this);
        saveRemaindder_btn = (Button) findViewById(R.id.save_event);

        setTimeAndDate();

        saveRemaindder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveRemainder();

            }
        });

    }

//    public void saveupdateRemainder() {
//        final String str_date = date.getText().toString();
//        final String str_time = time.getText().toString();
//        final String str_title = title.getText().toString();
//        final String str_message = message.getText().toString();
//        eventDatabase.updateData(id, str_date, str_title, str_message, getIntent().getStringExtra("alarmId"), str_time);
//        Toast.makeText(this, "Remainder updated!", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(AddEvent.this, RemainderListActivity.class));
//
//
//    }


    private void set_alarm(long time, int alarm_id) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Log.v("alarm_id", alarm_id + "");
        Intent intent = new Intent(AddEvent.this, AlarmRecever.class);

        intent.putExtra("message", message.getText().toString());
        intent.putExtra("title", title.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEvent.this, alarm_id, intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time * 1000, pendingIntent);


        Toast.makeText(AddEvent.this, "Alamr is set", Toast.LENGTH_LONG).show();

    }


    public void saveRemainder() {

        final String str_date = date.getText().toString();
        final String str_time = time.getText().toString();
        final String str_title = title.getText().toString();
        final String str_message = message.getText().toString();
        final Calendar myCalendar1 = Calendar.getInstance();

        int year = myCalendar1.get(Calendar.YEAR);
        int month = myCalendar1.get(Calendar.MONTH);
        int day = myCalendar1.get(Calendar.DAY_OF_MONTH);

        str_current = year + ":" + month + ":" + day;
        GetDifferenceOfTime getDifferenceOfTime = new GetDifferenceOfTime();
        int alarm_id = (int) SystemClock.currentThreadTimeMillis();
        eventDatabase.insertRemainder(str_date, str_title, str_message, alarm_id + "", str_time);

        set_alarm(getDifferenceOfTime.getDifferece(str_current, str_date, str_time), alarm_id);

        startActivity(new Intent(AddEvent.this, RemainderListActivity.class));
    }

    public void setTimeAndDate() {

        myCalendar1 = Calendar.getInstance();
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + " : " + minute);

                    }
                }, hour, minutes, true);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();

            }
        });

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd:MM:yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date.setText(sdf.format(myCalendar1.getTime()));
            }

        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEvent.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

}
