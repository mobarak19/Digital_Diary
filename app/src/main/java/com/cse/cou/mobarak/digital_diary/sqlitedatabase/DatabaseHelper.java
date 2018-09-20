package com.cse.cou.mobarak.digital_diary.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mobarak on 7/12/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE="contact.db";
    public static final String TABLE_NAME="contact";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="PHONE";
    public static final String COL_4="EMAIL";
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT)";
    public static final String UPDATE_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UPDATE_TABLE);
        onCreate(db);

    }
    public boolean insertData(String name,String phone,String email){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);
        contentValues.put(COL_4,email);
        long r=database.insert(TABLE_NAME,null,contentValues);
        if(r==-1){
            return false;
        }else {
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor res=database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;

    }
    public boolean deleteData( String id){
        SQLiteDatabase database = this.getWritableDatabase();
        int i=database.delete(TABLE_NAME,"ID=?",new String[]{id});
        return i>0;
    }
    public boolean updateData(String id,String name,String phone,String email){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);
        contentValues.put(COL_4,email);
        int i=database.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return i>0;
    }

}
