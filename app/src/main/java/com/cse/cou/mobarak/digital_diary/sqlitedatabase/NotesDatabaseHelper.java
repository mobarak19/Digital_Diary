package com.cse.cou.mobarak.digital_diary.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sahida on 7/13/2018.
 */

public class NotesDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "notes.db";
    public static final String TABLE_NAME = "notes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "TITLE";
    public static final String COL_4 = "DESCRIPTION";
    public static final String CREATE_TABLE = "CREATE TABLE notes(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, TITLE TEXT, DESCRIPTION TEXT)";
    public static final String UPDATE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public NotesDatabaseHelper(Context context) {
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

    public boolean insertNotes(String date, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, title);
        contentValues.put(COL_4, description);
        long r = db.insert(TABLE_NAME, null, contentValues);
        if (r == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;

    }
    public boolean deleteData(String id){

        SQLiteDatabase database = this.getWritableDatabase();
        int i=database.delete(TABLE_NAME,"ID=?",new String[]{id});
        return i>0;
    }
    public boolean updateData(String id,String date,String title,String description){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,title);
        contentValues.put(COL_4,description);
       int i= database.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return i>0;
    }
}
