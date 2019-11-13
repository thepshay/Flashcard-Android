package com.example.android.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SetsDbHelper extends SQLiteOpenHelper {

    //Set up SQLite tables and columns
    public static final String DATABASE_NAME = "sets.db";
    public static final String TABLE_NAME = "sets_table";
    public static final String COL_ID = "id";
    public static final String COL__NAME = "name";
    public static final String COL_DES = "description";

    private static final int DATABASE_VERSION = 1;


    SetsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //insert name of set and description into database
    void insertData(String name, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL__NAME, name);
        cv.put(COL_DES,des);
        db.insert(TABLE_NAME, null, cv);

    }

    //returns cursor that retrieve all table data
    Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

}
