package com.example.adam.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adam.myapplication.Database.DBHelper;

/**
 * Created by adam on 8/24/16.
 */
public class DataBaseActivity extends Activity implements View.OnClickListener {

    Button insertBtn, deleteBtn, lookupBtn, updateBtn;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        insertBtn = (Button) findViewById(R.id.insertBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        lookupBtn = (Button) findViewById(R.id.lookupBtn);
        updateBtn = (Button) findViewById(R.id.updatetBtn);

        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        lookupBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

        iniDB();
    }

    private void iniDB() {
        dbHelper = new DBHelper(DataBaseActivity.this);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insertBtn:
                byte[] data = new byte[2];
                data[0] = 7;
                data[1] = 8;
                ContentValues cv = new ContentValues();
                cv.put("name", "test");
                cv.put("data", data);
                try {
                    db.beginTransaction();
                    db.insert("repo", null, cv);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                Cursor cursor1 = db.rawQuery("select last_insert_rowid() from repo", null);
                if(cursor1.moveToNext()) {
                    int lastid = cursor1.getInt(0);
                    Toast.makeText(DataBaseActivity.this, "last id :" + lastid, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(DataBaseActivity.this, "Insert Success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteBtn:
                break;
            case R.id.lookupBtn:
                Cursor cursor = db.query("repo", new String[] {"id", "name", "data"}, null, null, null, null, null);
                StringBuilder sb = new StringBuilder();
                while(cursor.moveToNext()) {
                    sb.append(cursor.getInt(cursor.getColumnIndex("id")));
                    sb.append(":");
                    sb.append(cursor.getString(cursor.getColumnIndex("name")));
                    sb.append("\n");
                    byte[] getData = cursor.getBlob(cursor.getColumnIndex("data"));
                    sb.append("byte data size is " + getData.length + " content is " + getData[0] + " " + getData[1]);
                    sb.append("\n");
                }
                Toast.makeText(DataBaseActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.updatetBtn:
                break;
        }
    }
}
