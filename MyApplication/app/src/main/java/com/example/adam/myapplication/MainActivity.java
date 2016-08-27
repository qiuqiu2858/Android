package com.example.adam.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.adam.myapplication.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;

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
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                if(checkPermission(permissions)) {
                    //startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else {
                    ActivityCompat.requestPermissions(this, permissions, 1);
                }
                break;
            case R.id.dbBtn:
                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
                break;
        }
    }

    public boolean checkPermission(String[] array) {
        List<String> list = new ArrayList<>();

        for(int i = 0; i < array.length; i++) {
            if(ContextCompat.checkSelfPermission(this, array[i]) != PackageManager.PERMISSION_GRANTED) {
                list.add(array[i]);
            }
        }
//
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG,"No write permission");
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//            //return false;
//        }
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
//            Log.d(TAG, "No camera permission");
//            return false;
//        }
        if(list.size() == 0)
            return true;
        else {
            return false;
        }
    }

    @Override
    public void  onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                for(int i = 0; i < permissions.length; i++) {
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (permissions[i].equals(Manifest.permission.CAMERA)) {
                            Log.d(TAG, "request camera failed");
                        } else {
                            Log.d(TAG, "request disk failed");
                        }
                    }
                }
                break;
        }
    }
}
