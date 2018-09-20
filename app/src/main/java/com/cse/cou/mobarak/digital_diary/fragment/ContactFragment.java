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


import com.cse.cou.mobarak.digital_diary.activity.AddContact;
import com.cse.cou.mobarak.digital_diary.R;
import com.cse.cou.mobarak.digital_diary.activity.ShowContactDetails;
import com.cse.cou.mobarak.digital_diary.model.ContactModel;
import com.cse.cou.mobarak.digital_diary.sqlitedatabase.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobarak on 7/11/2018.
 */

public class ContactFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    List list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.contact,container,false);

        databaseHelper=new DatabaseHelper(getContext());
        getActivity().setTitle("Contact List");
        floatingActionButton= (FloatingActionButton) view.findViewById(R.id.add_contract);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddContact.class));
            }
        });
        recyclerView= (RecyclerView) view.findViewById(R.id.contact_list_recycle_view);

        list=new ArrayList();
        getPupulated();



        return view;
    }

    public void getPupulated(){
        list.clear();
        Cursor cursor=databaseHelper.getAllData();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(),"Not contact is found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                ContactModel contactModel=new ContactModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                list.add(contactModel);
            }
        }

        MyAdapter adapter=new MyAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {


        List<ContactModel> contactModels;

        public MyAdapter(List<ContactModel> models){
            contactModels=models;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View inflater=LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,parent,false);

            inflater.setOnClickListener(this);
            inflater.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    final ContactModel item = contactModels.get(itemPosition);

                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext()).setTitle("Confirmation message")
                            .setMessage("Are you want to delete this note?").setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(databaseHelper.deleteData(item.getId())){
                                        getPupulated();
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

            MyViewHolder myViewHolder=new MyViewHolder(inflater);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {

            ContactModel contactModel=contactModels.get(position);
            holder.name.setText(contactModel.getName());

        }

        @Override
        public int getItemCount() {
            return contactModels.size();
        }

        @Override
        public void onClick(View v) {

            int itemPosition = recyclerView.getChildLayoutPosition(v);
            ContactModel item = contactModels.get(itemPosition);
            Intent intent=new Intent(getContext(),ShowContactDetails.class);
            intent.putExtra("id",item.getId());
            intent.putExtra("name",item.getName());
            intent.putExtra("phone",item.getPhone());
            intent.putExtra("email",item.getEmail());
            startActivity(intent);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;

            public MyViewHolder(View itemView) {
                super(itemView);
                name= (TextView) itemView.findViewById(R.id.list_name);
            }
        }
    }
}
