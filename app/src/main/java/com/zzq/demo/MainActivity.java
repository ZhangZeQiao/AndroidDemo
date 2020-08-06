package com.zzq.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import com.zzq.demo.annotation.TaskStatus;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // startActivity(new Intent(this, SingletonKtActivity.class));
        mTv = (TextView) findViewById(R.id.tv);
        // test();
        doTask(TaskStatus.UN_KNOW);
    }

    @WorkerThread
    private void test() {
        mTv.setText("---测试---");
    }

    private void doTask(@TaskStatus int status) {
        switch (status) {
            case TaskStatus.UN_KNOW:
                break;
            case TaskStatus.UN_START:
                break;
            case TaskStatus.PROGRESSING:
                break;
            case TaskStatus.COMPLETED:
                break;
            default:
                break;
        }
    }
}
