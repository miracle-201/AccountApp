package com.example.accountapp.db

data class TypeBean(
    var id:Int,           //编号
    var typename:String,  //类型名称
    var imageId:Int,     //未选中图片id
    var simageId:Int,    //选中图片id
    var kind:Int)        //收入 1，支出 0

