package com.zzq.demo.util;

import android.content.Context;
import android.widget.Toast;

public class TestUtils {

    private TestUtils() {

    }

    public static void toast(Context context) {
        Toast.makeText(context, "TestUtils.toast", Toast.LENGTH_SHORT).show();
    }
}
