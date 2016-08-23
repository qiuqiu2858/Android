package com.example.adam.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.adam.myapplication.Entity.Repo;

import java.util.List;

/**
 * Created by adam on 8/23/16.
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(List<Repo> repos) {
        db.beginTransaction();
        try {
            for (Repo r : repos) {
                db.execSQL("INSERT INTO repo VALUES(null, ?, ?, ?)", new Object[]{r.name});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


}
