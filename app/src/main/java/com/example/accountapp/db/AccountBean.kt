package com.example.accountapp.db
/*
*描述记录一条数据的相关内容类
 */
data class AccountBean(
    var id: Int = 0,                // ID
    var typename: String = "",      // 类型
    var simageId: Int = 0,         // 被选中类型图片
    var beizhu: String = "",        // 备注
    var money: Float = 0.0f,       // 价格
    var time: String = "",          // 保存时间字符串
    var year: Int = 0,              // 年
    var month: Int = 0,             // 月
    var day: Int = 0,               // 日
    var kind: Int = 0               // 类型：收入 --- 1；支出 --- 0
)
         // 类型：收入 --- 1；支出 --- 0)