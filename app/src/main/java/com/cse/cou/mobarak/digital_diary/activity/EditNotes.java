package com.cse.cou.mobarak.digital_diary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.NotesDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNotes extends AppCompatActivity {

    boolean isadded_clicked;
    NotesDatabaseHelper notesDatabaseHelper;
    Button save;
    EditText title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        setTitle("Edit Notes");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        save = (Button) findViewById(R.id.save_notes);

        isadded_clicked = getIntent().getBooleanExtra("add_button", false);
        title = (EditText) findViewById(R.id.gettitle);
        if (getIntent().getStringExtra("title") != null) {
            title.setText(getIntent().getStringExtra("title") + "");

        }
        description = (EditText) findViewById(R.id.getdescription);
        if (getIntent().getStringExtra("description") != null) {
            description.setText(getIntent().getStringExtra("description") + "");

        }
        notesDatabaseHelper = new NotesDatabaseHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isadded_clicked) {
                    saveData();

                } else {
                    updateData();

                }
            }
        });
    }

    private void updateData() {
       String id= getIntent().getStringExtra("id");
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm");
        String date = ft.format(dNow);
        if(notesDatabaseHelper.updateData(id,date,title.getText().toString(),description.getText().toString())){
            Toast.makeText(getBaseContext(), "1 note updated!" + date, Toast.LENGTH_LONG).show();
            startActivity(new Intent(EditNotes.this,NotesActivity.class));
            finish();


        }else {
            Toast.makeText(getBaseContext(), "could not updated!" + date, Toast.LENGTH_LONG).show();

        }

    }

    private void saveData() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm");
        String date = ft.format(dNow);


        if (notesDatabaseHelper.insertNotes(date, title.getText().toString(), description.getText().toString())) {
            Toast.makeText(getBaseContext(), "1 note saved!" + date, Toast.LENGTH_LONG).show();

            startActivity(new Intent(EditNotes.this, NotesActivity.class));
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Could not save note!" + date, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isadded_clicked) {
            saveData();

        } else {
            updateData();

        }
        startActivity(new Intent(EditNotes.this,MainActivity.class));
        finish();

    }
}
