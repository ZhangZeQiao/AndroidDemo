package com.zzq.demo.done2function.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.List;

public class NumbersDao extends BaseDao<NumbersDBHelper> {

    private static volatile NumbersDao mNumbersDao;

    static NumbersDao getInstance(@Nullable Context context) {
        if (mNumbersDao == null) {
            synchronized (NumbersDao.class) {
                if (mNumbersDao == null) {
                    mNumbersDao = new NumbersDao(context);
                }
            }
        }
        return mNumbersDao;
    }

    private NumbersDao(@Nullable Context context) {
        super(context);
    }

    @Override
    protected NumbersDBHelper initDbHelper(Context context) {
        return new NumbersDBHelper(context);
    }

    public boolean insert(NumberBean number) {
        boolean isSuccess = false;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(NumbersDBHelper.TABLE.NUMNAME, number.getNumName());
            values.put(NumbersDBHelper.TABLE.DESCRIPTION, number.getDescription());
            writableDatabase.insertOrThrow(NumbersDBHelper.TABLE.NAME, null, values);
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeWritableDatabase(writableDatabase);
        return isSuccess;
    }

    public boolean insert(List<NumberBean> numbers) {
        boolean isSuccess = false;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        // TODO 手动设置开始事务
        writableDatabase.beginTransaction();
        try {
            for (NumberBean number : numbers) {
                ContentValues values = new ContentValues();
                values.put(NumbersDBHelper.TABLE.NUMNAME, number.getNumName());
                values.put(NumbersDBHelper.TABLE.DESCRIPTION, number.getDescription());
                writableDatabase.insertOrThrow(NumbersDBHelper.TABLE.NAME, null, values);
            }
            // TODO 设置事务处理成功，不设置会自动回滚不提交
            writableDatabase.setTransactionSuccessful();
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // TODO 只有执行了endTransaction方法，事务操作才会真正提交到数据库
            writableDatabase.endTransaction();
            // 关闭数据库连接
            // writableDatabase.close();
            closeWritableDatabase(writableDatabase);
        }
        return isSuccess;
    }

    public boolean deleteById(int id) {
        boolean isSuccess = false;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            writableDatabase.delete(
                    NumbersDBHelper.TABLE.NAME,// 表名
                    String.format("%s=%s", NumbersDBHelper.TABLE.ID, id),// 条件
                    null // 上面条件的占位符（ You may include ?s in the where clause）（可以，也可以不用）
            );
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeWritableDatabase(writableDatabase);
        return isSuccess;
    }

    public boolean updateById(int id, NumberBean number) {
        if (number == null) {
            return false;
        }
        boolean isSuccess = false;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(NumbersDBHelper.TABLE.NUMNAME, number.getNumName());
            values.put(NumbersDBHelper.TABLE.DESCRIPTION, number.getDescription());
            writableDatabase.update(
                    NumbersDBHelper.TABLE.NAME,// 表名
                    values,
                    String.format("%s=%s", NumbersDBHelper.TABLE.ID, id),// 条件
                    null // 上面条件的占位符（ You may include ?s in the where clause）（可以，也可以不用）
            );
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeWritableDatabase(writableDatabase);
        return isSuccess;
    }

    public NumberBean queryById(int id) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        NumberBean number = null;
        try {
            // SELECT * FROM Numbers WHERE id=9
            Cursor cursor = readableDatabase.query(
                    NumbersDBHelper.TABLE.NAME,// 表名
                    null, // 返回的字段，null表示返回全部字段
                    String.format("%s=%s", NumbersDBHelper.TABLE.ID, id), // 查询条件
                    null, // 查询条件若带有问号，这里既是问号所包括数据数组，没有即为null
                    null, // 分组的字段
                    null, // having
                    null // 排序方式
            );
            number = new NumberBean();
            // Move the cursor to the first row.This method will return false if the cursor is empty.
            if (cursor.moveToFirst()) {
                number.setId(cursor.getInt(cursor.getColumnIndex(NumbersDBHelper.TABLE.ID)));
                number.setNumName(cursor.getString(cursor.getColumnIndex(NumbersDBHelper.TABLE.NUMNAME)));
                number.setDescription(cursor.getString(cursor.getColumnIndex(NumbersDBHelper.TABLE.DESCRIPTION)));
            }
            // TODO 关闭cursor，回收资源
            cursor.close();
            closeReadableDatabase(readableDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

}
