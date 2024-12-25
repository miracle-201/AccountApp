package com.example.accountapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.accountapp.db.TypeBean
import com.example.accountapp.R

class TypeBaseAdapter(private val context:Context,private val mDatas:List<TypeBean>):BaseAdapter() {
    var selectPos=0 //选中位置
    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int,  convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_record_gv, parent, false)

        // 查找布局中的控件
        val iv: ImageView = view.findViewById(R.id.item_record_iv)
        val tv: TextView = view.findViewById(R.id.item_record_tv)

        // 获取指定位置的数据源
        val typeBean = mDatas[position]
        tv.text = typeBean.typename

        // 判断当前位置是否为选中位置，如果是选中位置，就设置为带颜色的图片，否则为灰色图片
        iv.setImageResource(if (selectPos == position) typeBean.simageId else typeBean.imageId)

        return view
    }
}