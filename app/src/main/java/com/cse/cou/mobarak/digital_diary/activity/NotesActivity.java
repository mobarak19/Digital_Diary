package com.cse.cou.mobarak.digital_diary.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.model.NotesModel;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.NotesDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    NotesDatabaseHelper databaseHelper;
    List list;
    boolean isadded_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setTitle("Notes");
        databaseHelper=new NotesDatabaseHelper(this);

        floatingActionButton= (FloatingActionButton) findViewById(R.id.add_notes);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NotesActivity.this,EditNotes.class);
                isadded_clicked=true;
                intent.putExtra("add_button",isadded_clicked);
                startActivity(intent);
            }
        });
        recyclerView= (RecyclerView) findViewById(R.id.notes_list_recycle_view);
        list=new ArrayList();
        getPopulated();
    }


    public void getPopulated(){
        list.clear();
        Cursor cursor=databaseHelper.getAllData();
        if(cursor.getCount()==0){
            Toast.makeText(NotesActivity.this,"Not contact is found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                NotesModel notesModel=new NotesModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                list.add(notesModel);
            }
        }

        MyAdapter adapter=new MyAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(NotesActivity.this));
        recyclerView.setAdapter(adapter);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {


        List<NotesModel> notesModels;

        public MyAdapter(List<NotesModel> models){
            notesModels=models;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View inflater= LayoutInflater.from(parent.getContext()).inflate(R.layout.notelist,parent,false);

            inflater.setOnClickListener(this);

            inflater.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    final NotesModel item = notesModels.get(itemPosition);

                    AlertDialog.Builder builder=new AlertDialog.Builder(NotesActivity.this).setTitle("Confirmation message")
                            .setMessage("Are you want to delete this note?").setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(databaseHelper.deleteData(item.getId())){
                                        getPopulated();

                                        Toast.makeText(NotesActivity.this,"1 note deleted!",Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(NotesActivity.this,"Could not delete!",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                    builder.show();

                    return true;
                }
            });

           MyAdapter.MyViewHolder myViewHolder=new MyAdapter.MyViewHolder(inflater);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {

            NotesModel notesModel=notesModels.get(position);
            holder.date.setText(notesModel.getDate());
            holder.title.setText(notesModel.getTitle());
            holder.desctiption.setText(notesModel.getDescription());

        }

        @Override
        public int getItemCount() {
            return notesModels.size();
        }

        @Override
        public void onClick(View v) {

            int itemPosition = recyclerView.getChildLayoutPosition(v);
            NotesModel item = notesModels.get(itemPosition);
            Intent intent=new Intent(NotesActivity.this,EditNotes.class);
            intent.putExtra("id",item.getId());
            intent.putExtra("date",item.getDate());
            intent.putExtra("title",item.getTitle());
            intent.putExtra("description",item.getDescription());
            startActivity(intent);
            finish();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView date;
            TextView title;
            TextView desctiption;

            public MyViewHolder(View itemView) {
                super(itemView);
                date= (TextView) itemView.findViewById(R.id.show_date);
                title= (TextView) itemView.findViewById(R.id.show_title);
                desctiption= (TextView) itemView.findViewById(R.id.show_description);
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(NotesActivity.this,MainActivity.class));
        finish();
    }
}
