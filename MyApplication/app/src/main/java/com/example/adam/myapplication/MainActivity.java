package com.example.adam.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.adam.myapplication.Database.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String TAG = MainActivity.class.getSimpleName();
    private Button btn,dbBtn;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        dbBtn = (Button) findViewById(R.id.dbBtn);
        btn.setOnClickListener(this);
        dbBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;
            case R.id.dbBtn:
                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
                break;
        }
    }
}
