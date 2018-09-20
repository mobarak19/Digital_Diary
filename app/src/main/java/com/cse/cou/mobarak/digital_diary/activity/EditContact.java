package com.cse.cou.mobarak.digital_diary.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.DatabaseHelper;

public class EditContact extends AppCompatActivity {

    Button save_update;
    TextView name,phone,email;
    DatabaseHelper databaseHelper;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        setTitle("Edit Contact");
        save_update= (Button) findViewById(R.id.save_update);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        id=getIntent().getStringExtra("id");
        name= (TextView) findViewById(R.id.edit_name);
        name.setText(getIntent().getStringExtra("name"));
        phone= (TextView) findViewById(R.id.edit_phone);
        phone.setText(getIntent().getStringExtra("phone"));

        databaseHelper=new DatabaseHelper(this);
        email= (TextView) findViewById(R.id.edit_email);
        email.setText(getIntent().getStringExtra("email"));

        save_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(databaseHelper.updateData(id,name.getText().toString(),phone.getText().toString(),email.getText().toString())){
                    Toast.makeText(EditContact.this,"Update successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditContact.this,MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(EditContact.this,"Could not update",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
