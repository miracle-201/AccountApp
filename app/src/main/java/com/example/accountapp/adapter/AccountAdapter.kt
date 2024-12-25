package com.example.accountapp.adapter

import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.accountapp.R
import com.example.accountapp.db.AccountBean


class AccountAdapter(private val context: Context, private val mDatas:List<AccountBean>):BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    val calendar: java.util.Calendar = java.util.Calendar.getInstance()
    private var year: Int = calendar.get(Calendar.YEAR)
    private var month: Int = calendar.get(Calendar.MONTH) + 1
    private var day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder: ViewHolder
        val view: View = convertView ?: inflater.inflate(R.layout.item_mainlv, parent, false).apply {
            holder = ViewHolder(this)
            tag = holder // 将 ViewHolder 存储在视图的标签中
        }.let { it } // 如果 convertView 不为 null，这里的 let 不会执行

        holder = convertView?.tag as? ViewHolder ?: ViewHolder(view) // 从 tag 中获取 ViewHolder

        val bean:AccountBean = mDatas[position]
        holder.typeIv.setImageResource(bean.simageId)
        holder.typeTv.text = bean.typename
        holder.beizhuTv.setText(bean.beizhu)
        holder.moneyTv.setText("￥ ${bean.money}")

        if (bean.year == year && bean.month == month && bean.day == day) {
            val time = bean.time.split(" ")[1] // 处理时间字符串
            holder.timeTv.setText("今天 $time")
        } else {
            holder.timeTv.setText(bean.time)
        }

        return view
    }
    private class ViewHolder(view:View){
        val typeIv: ImageView = view.findViewById(R.id.item_mainlv_iv)
        val typeTv: TextView = view.findViewById(R.id.item_mainlv_tv_title)
        val timeTv: TextView = view.findViewById(R.id.item_mainlv_tv_time)
        val beizhuTv: TextView = view.findViewById(R.id.item_mainlv_tv_beizhu)
        val moneyTv: TextView = view.findViewById(R.id.item_mainlv_tv_money)
    }
}