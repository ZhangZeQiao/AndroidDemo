package com.zzq.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // startActivity(new Intent(this, HaMainActivity.class));

        mTv = (TextView) findViewById(R.id.tv);

        Log.v("---zzq---111", "" + Thread.currentThread().getName());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.v("---zzq---222", "" + Thread.currentThread().getName());
            }
        };
        WorkThreadHandler.runInWorkThreadDelay(runnable, 3000);
        WorkThreadHandler.removeWorkThreadRunnable(runnable);
    }

}
