package com.zzq.demo.done2function.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

abstract class BaseDao<T extends SQLiteOpenHelper> {

    private final T mDbHelper;
    private int mOpenCounts;
    private SQLiteDatabase mWritableDatabase;

    BaseDao(@Nullable Context context) {
        this.mDbHelper = initDbHelper(context);
    }

    abstract T initDbHelper(Context context);

    synchronized SQLiteDatabase getWritableDatabase() {
        if (mOpenCounts == 0) {
            mWritableDatabase = mDbHelper.getWritableDatabase();
        }
        mOpenCounts++;
        return mWritableDatabase;
    }

    synchronized void closeWritableDatabase(SQLiteDatabase db) {
        mOpenCounts--;
        if (mOpenCounts <= 0) {
            db.close();
        }
    }

    SQLiteDatabase getReadableDatabase() {
        return mDbHelper.getReadableDatabase();
    }

    void closeReadableDatabase(SQLiteDatabase db) {
        db.close();
    }
}
