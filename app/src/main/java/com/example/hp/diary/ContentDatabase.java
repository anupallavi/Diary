package com.example.hp.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

import static com.example.hp.diary.R.id.date;

/**
 * Created by Hp on 4/29/2017.
 */

class ContentDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "diary.db";
    public static final String TABLE_NAME = "diary";
    public static final String COL1 = "date";
    public static final String COL2 = "content";
    public static final String FILE_DIR = "diaryContent";


    public ContentDatabase(Context context) {

        // super(context, DATABASE_NAME, null, 1);
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR + File.separator + DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (date DATE PRIMARY KEY, " +
                " content TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String sendDate(int dayOfMonth, int month, int year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = dayOfMonth + "/" + month + "/"+ year;
      //  ContentValues contentValues = new ContentValues();
        Cursor cursor = db.query(TABLE_NAME,new String[] {COL2}, COL1 + "=?",new String[] {date},null,null,null,null);
       // return date;
        if (cursor.moveToNext()) {
            String content = cursor.getString(0);
           return content;
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, date);
            contentValues.put(COL2, "");
            db.insert(TABLE_NAME,null,contentValues);
            //String sql1 = "insert into " + TABLE_NAME + "(" + COL1 + "," + COL2+ ") values( date,'');";
            //db.execSQL(sql1);
            return "";
        }

    }

    public boolean addData(String content, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,date);
        contentValues.put(COL2, content);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}


