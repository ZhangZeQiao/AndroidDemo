package com.zzq.demo.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zzq.demo.R

class SingletonKtActivity : AppCompatActivity() {

    // 一、饿汉式实现单例模式
    object SingletonDemo01

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt_singleton)

    }

}