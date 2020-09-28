package com.zzq.demo.done2function.sqlite.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase mAppDatabase;

    // TODO 在实例化 AppDatabase 对象时应遵循单例设计模式。每个 RoomDatabase 实例的成本相当高，几乎不需要在单个进程中访问多个实例。
    public static AppDatabase getInstance(Context context) {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "dbRoomTest.db")
                            .addMigrations()
                            // 默认不允许在主线程中连接数据库
                            // TODO: 2020/9/28 test
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return mAppDatabase;
    }

    public abstract UserDao userDao();
}
