package com.example.adam.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adam on 8/23/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS repo" +
        "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, data BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("ALTER TABLE repo ADD COLUMN other STRING");
    }
}
