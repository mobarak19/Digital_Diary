package com.cse.cou.mobarak.digital_diary.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.mobarak.digital_diary.activity.EditNotes;
import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.model.NotesModel;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.NotesDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobarak on 7/11/2018.
 */

public class NotesFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    NotesDatabaseHelper databaseHelper;
    List list;
    boolean isadded_clicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.notes,container,false);

        getActivity().setTitle("Notes");

        databaseHelper=new NotesDatabaseHelper(getContext());

        floatingActionButton= (FloatingActionButton) view.findViewById(R.id.add_notes);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),EditNotes.class);
                isadded_clicked=true;
                intent.putExtra("add_button",isadded_clicked);
                startActivity(intent);            }
        });
        recyclerView= (RecyclerView) view.findViewById(R.id.notes_list_recycle_view);
        list=new ArrayList();
        getPopulated();


        return view;
    }


    public void getPopulated(){
        list.clear();
        Cursor cursor=databaseHelper.getAllData();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(),"Not contact is found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                NotesModel notesModel=new NotesModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                list.add(notesModel);
            }
        }

        MyAdapter adapter=new MyAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {


        List<NotesModel> notesModels;

        public MyAdapter(List<NotesModel> models){
            notesModels=models;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View inflater=LayoutInflater.from(parent.getContext()).inflate(R.layout.notelist,parent,false);

            inflater.setOnClickListener(this);

            inflater.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    final NotesModel item = notesModels.get(itemPosition);

                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext()).setTitle("Confirmation message")
                            .setMessage("Are you want to delete this note?").setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(databaseHelper.deleteData(item.getId())){
                                        getPopulated();

                                        Toast.makeText(getContext(),"1 note deleted!",Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(getContext(),"Could not delete!",Toast.LENGTH_LONG).show();

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
            Intent intent=new Intent(getContext(),EditNotes.class);
            intent.putExtra("id",item.getId());
            intent.putExtra("date",item.getDate());
            intent.putExtra("title",item.getTitle());
            intent.putExtra("description",item.getDescription());
            startActivity(intent);
            getActivity().finish();
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
}
