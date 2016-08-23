package com.example.adam.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by adam on 8/23/16.
 */
public class SecondActivity extends Activity {

    public final static String TAG = SecondActivity.class.getSimpleName();
    TestTask task = null;
    boolean working = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        working = true;
        task = new TestTask();
        task.execute();
    }

    @Override
    protected void onPause() {
        Log.w(TAG, "onPause");
        super.onPause();
        task.cancel(true);
        task = null;
        Log.w(TAG, "Task set null");
    }

    @Override
    protected void onDestroy() {
        Log.w(TAG, "onDestroy");
        super.onDestroy();
    }

    private class TestTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        private int cnt = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            while(true) {
                if(isCancelled())
                    break;
                Log.w(TAG, "this is a log " + cnt++);
                SystemClock.sleep(2000);
            }
            Log.w(TAG, "is canceled");
            return null;
        }
    }
}
