package com.cse.cou.mobarak.digital_diary.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mobarak on 7/16/2018.
 */

public class EventDatabase extends SQLiteOpenHelper {

    public static final String database_name="create.event.db";
    public static final String table_name="myevent";
    public static final String col1="id";
    public static final String col2="dat";
    public static final String col3="title";
    public static final String col4="message";
    public static final String col5="alarmId";
    public static final String col6="time";
    public static final String create_table="CREATE TABLE "+table_name+"("+col1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            col2+" TEXT, "+
            col3+" TEXT, "+
            col4+" TEXT, "+
            col5+" TEXT, "+
            col6+" TEXT)";
    public static final String upgrade_tabe="DROP TABLE IF EXISTS "+table_name;

    public EventDatabase(Context context) {
        super(context, database_name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(upgrade_tabe);
        onCreate(db);
    }

    public boolean insertRemainder(String date,String title,String message,String alarm_id,String time){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,date);
        contentValues.put(col3,title);
        contentValues.put(col4,message);
        contentValues.put(col5,alarm_id);
        contentValues.put(col6,time);

        long i=database.insert(table_name,null,contentValues);

        if(i==-1){
            return false;
        }else {
            return true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor res=database.rawQuery("SELECT * FROM "+table_name,null);

        return res;

    }
    public boolean deleteData( String id){
        SQLiteDatabase database = this.getWritableDatabase();
        int i=database.delete(table_name,"id=?",new String[]{id});
        return i>0;
    }
    public boolean updateData(String id,String date,String title,String message,String alarm_id,String time){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,date);
        contentValues.put(col3,title);
        contentValues.put(col4,message);
        contentValues.put(col5,alarm_id);
        contentValues.put(col6,time);
        int i= database.update(table_name,contentValues,"id=?",new String[]{id});
        return i>0;
    }
}
