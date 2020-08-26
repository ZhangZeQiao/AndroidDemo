package com.zzq.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private CountdownView mCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // startActivity(new Intent(this, HaMainActivity.class));

        mTv = (TextView) findViewById(R.id.tv);
        mCv = (CountdownView) findViewById(R.id.cv);

        mHandler.sendEmptyMessage(1);
    }

    private int mCount = 10;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            mHandler.sendEmptyMessageDelayed(1, 1000);
            mCount--;
            mCv.setCountdownValues(mCount);
            return false;
        }
    });

}
