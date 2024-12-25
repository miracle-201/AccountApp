package com.example.accountapp

import android.app.Application
import com.example.accountapp.db.DBManager
import com.example.accountapp.db.TypeBean
import java.lang.reflect.Type

class UniteApp:Application() {
    private val dbManager = DBManager()
    override fun onCreate() {
        super.onCreate()
        //初始化数据库
        dbManager.initDB(applicationContext)
    }
}