package com.example.android.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Set;

public class WordsDbHelper extends SQLiteOpenHelper {

        //Set up SQLite tables and columns
        public static final String DATABASE_NAME = "words.db";
        public static final String TABLE_NAME = "words_table";
        public static final String COL_ID = "id";
        public static final String COL_SET_ID = "set_id";
        public static final String COL_WORD = "word";
        public static final String COL_DEFINITION = "definition";

        private static final int DATABASE_VERSION = 1;

        public WordsDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "set_id TEXT NOT NULL, " +
                    "word TEXT NOT NULL, " +
                    "definition TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void insertData(String set, String word, String def){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_SET_ID, set);
            contentValues.put(COL_WORD, word);
            contentValues.put(COL_DEFINITION,def);
            db.insert(TABLE_NAME, null, contentValues);
        }

        Cursor getAllData(String tempSetId){
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("SELECT * FROM words_table WHERE set_id = ?", new String[]{tempSetId});
        }
}
