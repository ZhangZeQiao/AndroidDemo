package com.zzq.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zzq.demo.service.MyService;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
        // startActivity(new Intent(this, KotlinActivity.class));

        serviceDemo();
    }

    private MyService.MyBinder mBinder;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //在Activity与Service解除关联的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //实例化Service的内部类myBinder
            //通过向下转型得到了MyBinder的实例
            mBinder = (MyService.MyBinder) service;
            //在Activity调用Service类的方法
            mBinder.service_connect_Activity();
        }
    };

    private void serviceDemo() {
        Intent intent = new Intent(this, MyService.class);
        /*startService(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        }, 3000L);*/
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

}
