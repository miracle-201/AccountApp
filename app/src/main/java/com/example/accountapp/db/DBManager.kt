package com.example.accountapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/*
*负责管理数据库的类
* 主要对于表当中的内容进行操作：增删改查
 */
class DBManager {
    private lateinit var db:SQLiteDatabase
    val dbName = "AccountApp.db"
    val dbVersion = 1
    fun initDB(context:Context){
        val helper=DBOpenhelper(context,dbName,dbVersion) //得到帮助类对象
        db=helper.writableDatabase //得到数据库对象
    }
    /*
*读取数据库当中的数据，写入内存集合
* 传入kind 表示输入或输出
 */
    fun getTypeList(kind:Int):List<TypeBean>{
        val list= mutableListOf<TypeBean>()//创建一个可变的列表来存储结果
        val sql="select*from typetb where kind=$kind"
        val cursor=db.rawQuery(sql,null)

        val typenameIndex = cursor.getColumnIndex("typename")
        val imageIdIndex = cursor.getColumnIndex("imageId")
        val simageIdIndex = cursor.getColumnIndex("simageId")
        val kindIndex = cursor.getColumnIndex("kind")
        val idIndex = cursor.getColumnIndex("id")
        // 循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            // 检查列索引是否有效
            if (typenameIndex != -1 && imageIdIndex != -1 && simageIdIndex != -1 && kindIndex != -1 && idIndex != -1) {
                val typename = cursor.getString(typenameIndex)
                val imageId = cursor.getInt(imageIdIndex)
                val simageId = cursor.getInt(simageIdIndex)
                val kind1 = cursor.getInt(kindIndex)
                val id = cursor.getInt(idIndex)

                val typeBean = TypeBean(id, typename, imageId, simageId, kind)
                list.add(typeBean)
            } else {
                // 如果某个列索引无效，可以选择抛出异常或打印日志
                Log.e("DatabaseError", "One or more column names are incorrect.")
            }
        }
            cursor.close()
            return list
    }
    /*
    *向记账表当中插入一条元素
     */
    fun insertItemToAccounttb(bean:AccountBean) {
        var Values = ContentValues().apply {
            put("typename", bean.typename)
            put("simageId", bean.simageId)
            put("beizhu", bean.beizhu)
            put("money", bean.money)
            put("time", bean.time)
            put("year", bean.year)
            put("month", bean.month)
            put("day", bean.day)
            put("kind", bean.kind)
        }
        db.insert("accounttb", null, Values)
    }

    /*
    *获取记账表当中某一天的所有支出或者收入情况
     */
    fun getAcntListFromAcnttb(year:Int,month:Int,day:Int):List<AccountBean>{
        val list = mutableListOf<AccountBean>() // 使用 MutableList 初始化列表
        val sql = "SELECT * FROM accounttb WHERE year=? AND month=? AND day=? ORDER BY id DESC"
        val cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), day.toString()))

        // 获取列名
        val columnCount = cursor.columnCount
        val columnNames = mutableListOf<String>()

        for (i in 0 until columnCount) {
            columnNames.add(cursor.getColumnName(i))
        }
        // 打印列名以进行调试
        println("Column names: $columnNames")

        // 遍历符合要求的每一行数据
        val idIndex = cursor.getColumnIndex("id")
        val typenameIndex = cursor.getColumnIndex("typename")
        val beizhuIndex = cursor.getColumnIndex("beizhu")
        val timeIndex = cursor.getColumnIndex("time")
        val simageIdIndex = cursor.getColumnIndex("simageId")
        val kindIndex = cursor.getColumnIndex("kind")
        val moneyIndex = cursor.getColumnIndex("money")

        while (cursor.moveToNext()) {
            if (typenameIndex != -1 && simageIdIndex != -1 && beizhuIndex != -1 && timeIndex != -1 && moneyIndex != -1 && kindIndex != -1 && idIndex != -1) {
                val id = cursor.getInt(idIndex)
                val typename = cursor.getString(typenameIndex)
                val beizhu = cursor.getString(beizhuIndex)
                val time = cursor.getString(timeIndex)
                val simageId = cursor.getInt(simageIdIndex)
                val kind = cursor.getInt(kindIndex)
                val money = cursor.getFloat(moneyIndex)
                // 创建 AccountBean 对象并添加到列表中
                val accountBean = AccountBean(id, typename, simageId, beizhu, money, time, year, month, day, kind)
                list.add(accountBean)
            }else {
                // 如果某个列索引无效，可以选择抛出异常或打印日志
                Log.e("DatabaseError", "One or more column names are incorrect.")
            }
        }

        cursor.close() // 确保关闭 cursor
        return list // 返回结果列表
    }
    /*
    *删除记账表某一条记录
     */
    fun deleteItemFromAccounttbById(id:Int):Int{
        val i=db.delete("accounttb","id=?", arrayOf(id.toString()))
        return i
    }
    /*
    *修改记账表某一条记录
     */
    fun changeItemFromAccounttcById(id:Int,moneyEt:String,beizhuEt:String):Int{
        val values=ContentValues().apply {
            put("money",moneyEt)
            put("beizhu",beizhuEt)
        }
        val i=db.update("accounttb",values,"id=?", arrayOf(id.toString()))
        return i
    }

    // 在DBManager类中添加
    @SuppressLint("Range")
    fun getTotalExpenses(year: Int, month: Int, day: Int): Float {
        var totalExpenses = 0f
        val cursor = db.rawQuery("SELECT money FROM accounttb WHERE year=? AND month=? AND day=? AND kind=0", arrayOf(year.toString(), month.toString(), day.toString()))
        while (cursor.moveToNext()) {
            totalExpenses += cursor.getFloat(cursor.getColumnIndex("money"))
        }
        cursor.close()
        return totalExpenses
    }

    @SuppressLint("Range")
    fun getTotalIncomes(year: Int, month: Int, day: Int): Float {
        var totalIncomes = 0f
        val cursor = db.rawQuery("SELECT money FROM accounttb WHERE year=? AND month=? AND day=? AND kind=1", arrayOf(year.toString(), month.toString(), day.toString()))
        while (cursor.moveToNext()) {
            totalIncomes += cursor.getFloat(cursor.getColumnIndex("money"))
        }
        cursor.close()
        return totalIncomes
    }

}


