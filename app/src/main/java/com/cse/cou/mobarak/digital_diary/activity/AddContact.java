package com.cse.cou.mobarak.digital_diary.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.DatabaseHelper;


public class AddContact extends AppCompatActivity {
    EditText name,email,phone;
    Button save;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        db=new DatabaseHelper(AddContact.this);

        save= (Button) findViewById(R.id.save_contact);
        name= (EditText) findViewById(R.id.save_name);
        email= (EditText) findViewById(R.id.save_email);
        phone= (EditText) findViewById(R.id.save_phone);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(attemptSave()){
                    if(db.insertData(name.getText().toString(),phone.getText().toString(),email.getText().toString())){
                        Toast.makeText(AddContact.this,"1 Contact added.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddContact.this,MainActivity.class));
                    }else {
                        Toast.makeText(AddContact.this,"Could not add.",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

    }

    private boolean attemptSave() {

        // Reset errors.
        name.setError(null);
        email.setError(null);
        phone.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError("Enter name...");
            focusView = name;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone.getText().toString())) {
            phone.setError("Enter phone...");
            focusView = phone;
            cancel = true;
        } else if (!isPhoneValid(phone.getText().toString())) {
            phone.setError("Enter a valid phone");
            focusView = phone;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Enter email...");
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(email.getText().toString())) {
            email.setError("Enter a valid email");
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            return true;
        }
        return false;

    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPhoneValid(String number) {
        return number.length() > 6;
    }
}
