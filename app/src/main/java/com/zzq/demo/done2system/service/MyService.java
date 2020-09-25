package com.zzq.demo.done2system.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    // 调用范例
    /*private MyService.MyBinder mBinder;
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
        *//*startService(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        }, 3000L);*//*
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }*/

    private final String TAG = this.toString();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("---zzq---", TAG + "---onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("---zzq---", TAG + "---onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public static class MyBinder extends Binder {
        public void service_connect_Activity() {
            Log.v("---zzq---", "Service关联了Activity,并在Activity执行了Service的方法");
        }
    }

    private Binder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v("---zzq---", TAG + "---onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v("---zzq---", TAG + "---onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onLowMemory() {
        Log.v("---zzq---", TAG + "---onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("---zzq---", TAG + "---onDestroy");
    }
}
