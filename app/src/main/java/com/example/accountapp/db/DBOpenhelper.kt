package com.example.accountapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.accountapp.R

class DBOpenhelper(val context: Context,name:String,version:Int)
    : SQLiteOpenHelper(context,name,null,version){

    override fun onCreate(db: SQLiteDatabase) {
        //创建表示类型的表
        var sql="create table typetb("+
                "id integer primary key autoincrement,"+
                "typename varchar(10),"+
                "imageId integer,simageId integer,"+
                "kind integer)"
        db.execSQL(sql)
        insertType(db)
        //创建记账表
        sql = "create table accounttb (" +
                "id integer primary key autoincrement, " +
                "typename varchar(10), " +
                "simageId integer, " +
                "beizhu varchar(80), " +
                "money float, " +
                "time varchar(60), " +
                "year integer, " +
                "month integer, " +
                "day integer, " +
                "kind integer)"
        db.execSQL(sql)
    }

    private fun insertType(db: SQLiteDatabase) {
//向typetb表中插入元素
        var sql="insert into typetb(typename,imageId,simageId,kind) values(?,?,?,?)"
        db.execSQL(sql, arrayOf("其他",R.mipmap.ic_qita, R.mipmap.ic_qita_fs,0))
        db.execSQL(sql, arrayOf("餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0))
        db.execSQL(sql, arrayOf("交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0))
        db.execSQL(sql, arrayOf("购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, 0))
        db.execSQL(sql, arrayOf("服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0))
        db.execSQL(sql, arrayOf("日用品", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0))
        db.execSQL(sql, arrayOf("娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0))
        db.execSQL(sql, arrayOf("零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0))
        db.execSQL(sql, arrayOf("烟酒茶", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0))
        db.execSQL(sql, arrayOf("学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0))
        db.execSQL(sql, arrayOf("医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0))
        db.execSQL(sql, arrayOf("住宅", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0))
        db.execSQL(sql, arrayOf("水电煤", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0))
        db.execSQL(sql, arrayOf("通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0))
        db.execSQL(sql, arrayOf("人情往来", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0))

        db.execSQL(sql, arrayOf("其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1))
        db.execSQL(sql, arrayOf("薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1))
        db.execSQL(sql, arrayOf("奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1))
        db.execSQL(sql, arrayOf("借入", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1))
        db.execSQL(sql, arrayOf("收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1))
        db.execSQL(sql, arrayOf("利息收入", R.mipmap.in_lixifuji, R.mipmap.in_lixifuji_fs, 1))
        db.execSQL(sql, arrayOf("投资回报", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1))
        db.execSQL(sql, arrayOf("二手交易", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1))
        db.execSQL(sql, arrayOf("意外所得", R.mipmap.in_yiwai, R.mipmap.in_yiwai_fs, 1))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }



}