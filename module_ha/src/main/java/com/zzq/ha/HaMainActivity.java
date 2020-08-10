package com.zzq.ha;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zzq.base.router.IRouterCallback;
import com.zzq.base.router.RouterManager;

public class HaMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ha_main);

        RouterManager.getInstance().getHengRouter().startHengMainActivity(this, new IRouterCallback() {
            @Override
            public void complete(String result) {
                Toast.makeText(HaMainActivity.this, result + "", Toast.LENGTH_LONG).show();
            }
        });
    }

}
