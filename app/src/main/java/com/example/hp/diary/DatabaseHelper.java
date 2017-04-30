package com.example.hp.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hp on 4/29/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "list.db";
    public static final String TABLE_NAME = "list_data";
    public static final String COL1 = "English";
    public static final String COL2 = "Kannada";


    public DatabaseHelper(Context context) {super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (English TEXT, " +
                " Kannada TEXT)";
        Log.e("DATABASE OPERATIONS","going to do");
        db.execSQL(createTable);
        Log.e("DATABASE OPERATIONS","database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //public boolean addData(String item1,String item2) {
    public void addData(String item1,String item2, SQLiteDatabase db) {

        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item1);
        contentValues.put(COL2, item2);
        //long result = db.insert(TABLE_NAME,null,contentValues);
        db.insert(TABLE_NAME,null,contentValues);
        Log.e("DATABASE OPERATION", "New row inserted");
        //if date as inserted incorrectly it will return -1
      /*  if (result == -1) {
            return false;
        } else {
            return true;
        }*/
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public Cursor translate(ArrayList<String> result){
        //SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("SELECT myDB.COL2 FROM myDB.TABLE_NAME WHERE myDB.COL1 = ?",new String[] {result.get(0)});
        //Cursor cursor = db.rawQuery("SELECT myDB.COL2 FROM myDB.TABLE_NAME WHERE myDB.COL1 like '"+result.get(0)+"'",null);
        Cursor cursor = db.query(TABLE_NAME,new String[] {COL2}, COL1 + "=?",new String[] {result.get(0).toLowerCase()},null,null,null,null);
        return cursor;



    }

}

