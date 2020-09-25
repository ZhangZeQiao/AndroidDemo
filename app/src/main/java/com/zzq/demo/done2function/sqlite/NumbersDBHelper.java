package com.zzq.demo.done2function.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NumbersDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dbTest.db";

    interface TABLE {
        String NAME = "Numbers";
        String ID = "id";
        String NUMNAME = "numName";
        String DESCRIPTION = "description";
    }

    NumbersDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 负责数据库的创建和初始化，只在第一次生成数据库的时候回调
     * <p>
     * TODO 只有第一次真正使用数据库时才会调用：
     * android.database.sqlite.SQLiteOpenHelper#getWritableDatabase()
     * android.database.sqlite.SQLiteOpenHelper#getReadableDatabase()
     * <p>
     * 文件路径：
     * data/data/[应用包名xxx.xxx.xxx]/databases/dbTest.db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format(
                "create table if not exists %s (%s integer primary key, %s text, %s text)",
                TABLE.NAME, TABLE.ID, TABLE.NUMNAME, TABLE.DESCRIPTION);
        db.execSQL(sql);
    }

    /**
     * 数据库升级的时候才会回调
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 每次成功打开数据库后首先被执行
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
