package com.zzq.demo.done2function.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.List;

public class NumbersDao {

    private final NumbersDBHelper mNumbersDBHelper;

    NumbersDao(@Nullable Context context) {
        mNumbersDBHelper = new NumbersDBHelper(context);
    }

    public boolean insert(NumberBean number) {
        try {
            SQLiteDatabase writableDatabase = mNumbersDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NumbersDBHelper.TABLE.NUMNAME, number.getNumName());
            values.put(NumbersDBHelper.TABLE.DESCRIPTION, number.getDescription());
            writableDatabase.insertOrThrow(NumbersDBHelper.TABLE.NAME, null, values);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(List<NumberBean> numbers) {
        boolean isSuccess = false;
        SQLiteDatabase writableDatabase = mNumbersDBHelper.getWritableDatabase();
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
        }
        return isSuccess;
    }

    public boolean deleteById(int id) {
        try {
            SQLiteDatabase writableDatabase = mNumbersDBHelper.getWritableDatabase();
            writableDatabase.delete(
                    NumbersDBHelper.TABLE.NAME,// 表名
                    String.format("%s=%s", NumbersDBHelper.TABLE.ID, id),// 条件
                    null // 上面条件的占位符（ You may include ?s in the where clause）（可以，也可以不用）
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateById(int id, NumberBean number) {
        if (number == null) {
            return false;
        }
        try {
            SQLiteDatabase writableDatabase = mNumbersDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NumbersDBHelper.TABLE.NUMNAME, number.getNumName());
            values.put(NumbersDBHelper.TABLE.DESCRIPTION, number.getDescription());
            writableDatabase.update(
                    NumbersDBHelper.TABLE.NAME,// 表名
                    values,
                    String.format("%s=%s", NumbersDBHelper.TABLE.ID, id),// 条件
                    null // 上面条件的占位符（ You may include ?s in the where clause）（可以，也可以不用）
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public NumberBean queryById(int id) {
        try {
            SQLiteDatabase readableDatabase = mNumbersDBHelper.getReadableDatabase();
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
            NumberBean number = new NumberBean();
            // Move the cursor to the first row.This method will return false if the cursor is empty.
            if (cursor.moveToFirst()) {
                number.setId(cursor.getInt(cursor.getColumnIndex(NumbersDBHelper.TABLE.ID)));
                number.setNumName(cursor.getString(cursor.getColumnIndex(NumbersDBHelper.TABLE.NUMNAME)));
                number.setDescription(cursor.getString(cursor.getColumnIndex(NumbersDBHelper.TABLE.DESCRIPTION)));
            } else {
                number = null;
            }
            // TODO 关闭cursor，回收资源
            cursor.close();
            return number;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
