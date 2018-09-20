package com.cse.cou.mobarak.digital_diary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.R;

import static com.cse.cou.mobarak.digital_diary.R.id.show_email;
import static com.cse.cou.mobarak.digital_diary.R.id.show_name;
import static com.cse.cou.mobarak.digital_diary.R.id.show_phone;

public class ShowContactDetails extends AppCompatActivity {

    Button back, edit;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact_details);

        setTitle("Contact Details");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        final String id = getIntent().getStringExtra("id");
        final String name = getIntent().getStringExtra("name");
        final String phone = getIntent().getStringExtra("phone");
        final String email = getIntent().getStringExtra("email");
        edit = (Button) findViewById(R.id.edit_contact);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowContactDetails.this, EditContact.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
        TextView n = (TextView) findViewById(show_name);
        TextView p = (TextView) findViewById(show_phone);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ("tel:" + phone);
                mIntent = new Intent(Intent.ACTION_CALL);
                mIntent.setData(Uri.parse(number));

                if (ContextCompat.checkSelfPermission(ShowContactDetails.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ShowContactDetails.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        startActivity(mIntent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        final TextView e = (TextView) findViewById(show_email);

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                startActivity(Intent.createChooser(intent, "Send"));

            }
        });
        n.setText("Name : " + name);
        p.setText("Phone : " + phone);
        e.setText("Email : " + email);

        back = (Button) findViewById(R.id.back_to_contact);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowContactDetails.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(mIntent);

                    // permission was granted, yay! Do the phone call

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ShowContactDetails.this,"Porblem occur!",Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
