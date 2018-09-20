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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.model.Event;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.EventDatabase;

import java.util.ArrayList;
import java.util.List;

public class RemainderListActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    List list;
    EventDatabase eventDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_list);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setTitle("Remainder List");




        floatingActionButton= (FloatingActionButton) findViewById(R.id.add_event);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RemainderListActivity.this,AddEvent.class));
            }
        });

        eventDatabase=new EventDatabase(this);
        list=new ArrayList();

        recyclerView= (RecyclerView) findViewById(R.id.event_recycleview);
        getPopulated();

    }


    public void getPopulated(){
        list.clear();
        Cursor cursor=eventDatabase.getAllData();
        if(cursor.getCount()==0){
            Toast.makeText(RemainderListActivity.this,"Not contact is found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                Event eventModel=new Event(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                list.add(eventModel);
            }
        }


        Myadapter adapter=new Myadapter(list);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }





    public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> implements View.OnClickListener {
        List<Event> eventList;
        public Myadapter(List<Event> list){
            eventList=list;
        }

        @Override
        public Myadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View inflater= LayoutInflater.from(RemainderListActivity.this).inflate(R.layout.event_list,parent,false);

            inflater.setOnClickListener(this);
            inflater.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    final Event item = eventList.get(itemPosition);

                    AlertDialog.Builder builder=new AlertDialog.Builder(RemainderListActivity.this).setTitle("Confirmation message")
                            .setMessage("Are you want to delete this note?").setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(eventDatabase.deleteData(item.getId())){
                                        getPopulated();
                                        Toast.makeText(RemainderListActivity.this,"1 remainder deleted!",Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(RemainderListActivity.this,"Could not delete!",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                    builder.show();
                    return true;
                }
            });
            Myadapter.MyViewHolder myViewHolder=new Myadapter.MyViewHolder(inflater);
            return myViewHolder;
        }



        @Override
        public void onBindViewHolder(Myadapter.MyViewHolder holder, int position) {


            Event e=eventList.get(position);
            holder.title.setText(e.getTitle());
            holder.date.setText(e.getDate());
            holder.time.setText(e.getTime());

        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        @Override
        public void onClick(View v) {


            int itemPosition = recyclerView.getChildLayoutPosition(v);
            Event item = eventList.get(itemPosition);
//            Intent intent=new Intent(RemainderListActivity.this,AddEvent.class);
//            intent.putExtra("id",item.getId());
//            intent.putExtra("date",item.getDate());
//            intent.putExtra("time",item.getTime());
//            intent.putExtra("title",);
//            intent.putExtra("message",item.getMessage());
//            intent.putExtra("alarmId",item.getAlarmId());
//            intent.putExtra("update","yes");

            AlertDialog.Builder builder=new AlertDialog.Builder(RemainderListActivity.this)
                    .setTitle("Alarm!")
                    .setMessage(item.getTitle()+" : "+item.getMessage())
                    .setIcon(R.drawable.ic_alarm_on_black_24dp)
                    .setPositiveButton("Ok",null);
            builder.show();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title,date,time;
            public MyViewHolder(View itemView) {
                super(itemView);
                title= (TextView) itemView.findViewById(R.id.event_title);
                date= (TextView) itemView.findViewById(R.id.event_date);
                time= (TextView) itemView.findViewById(R.id.event_time);
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RemainderListActivity.this,MainActivity.class));
    }
}
