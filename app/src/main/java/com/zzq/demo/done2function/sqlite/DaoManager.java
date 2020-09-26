package com.zzq.demo.done2function.sqlite;

import android.content.Context;

import androidx.annotation.Nullable;

public class DaoManager {

    public static NumbersDao getNumbersDao(@Nullable Context context) {
        return NumbersDao.getInstance(context.getApplicationContext());
    }

}
