package com.zzq.demo.done2function.sqlite;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zzq.demo.R;
import com.zzq.demo.done2function.sqlite.room.AppDatabase;
import com.zzq.demo.done2function.sqlite.room.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    /*public void onInsert(View view) {
     *//*NumberBean number = new NumberBean();
        number.setNumName("张" + 9);
        number.setDescription("zhang" + 9);
        DaoManager.getNumbersDao(this).insert(number);*//*
        ArrayList<NumberBean> numbers = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            NumberBean number = new NumberBean();
            number.setNumName("张" + i);
            number.setDescription("zhang" + i);
            numbers.add(number);
        }
        DaoManager.getNumbersDao(this).insert(numbers);
    }

    public void onDeleteById(View view) {
        DaoManager.getNumbersDao(this).deleteById(99);
    }

    public void onUpdateById(View view) {
        NumberBean number = new NumberBean();
        number.setNumName("张9改");
        number.setDescription("zhang9change");
        DaoManager.getNumbersDao(this).updateById(9, number);
    }

    public void onQueryById(View view) {
        NumberBean numberBean = DaoManager.getNumbersDao(this).queryById(9);
        if (numberBean != null) {
            String numName = numberBean.getNumName();
        }
    }*/

    public void onInsert(View view) {
        /*User user = new User();
        user.firstName = "joe";
        user.lastName = "zhang";
        AppDatabase.getInstance(this).userDao().insertUser(user);*/

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            User user = new User();
            user.firstName = "No" + i;
            user.lastName = "number" + i;
            users.add(user);
        }
        AppDatabase.getInstance(this).userDao().insertUsers(users);

        /*User user = new User();
        user.uid = 2;
        user.firstName = "No改";
        user.lastName = "number改";
        AppDatabase.getInstance(this).userDao().insertUser(user);*/
    }

    public void onDeleteById(View view) {
        User user = new User();
        user.uid = 2;
        /*user.firstName = "joe";
        user.lastName = "zhang";*/
        AppDatabase.getInstance(this).userDao().delete(user);
    }

    public void onUpdateById(View view) {
        User user = new User();
        user.uid = 2;
        user.firstName = "No改";
        user.lastName = "number改";
        AppDatabase.getInstance(this).userDao().updateUser(user);
    }

    public void onQueryById(View view) {
        List<User> all = AppDatabase.getInstance(this).userDao().loadAll();
        User user = all.get(0);
    }

}
