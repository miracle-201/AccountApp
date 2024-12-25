package com.example.accountapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.accountapp.adapter.AccountAdapter
import com.example.accountapp.db.AccountBean
import com.example.accountapp.db.DBManager
import com.example.accountapp.db.TypeBean
import com.example.accountapp.ui.theme.AccountAppTheme
import java.util.Calendar

class MainActivity : Activity() {
    private lateinit var todayLv:ListView //展示今日收支情况的Listview
    lateinit var mDatas: MutableList<AccountBean> //声明数据源
    var year:Int = 0
    var month:Int = 0
    var day:Int = 0
    private lateinit var adapter:AccountAdapter
    private lateinit var headerView:View
    private lateinit var topOutTv:TextView
    private lateinit var topInTv:TextView
    private lateinit var topbudgetTv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        initTime()
        todayLv=findViewById(R.id.main_lv)
        setLVLongClickListener()

        //添加ListView头布局
        addLVHeaderView();
        mDatas= mutableListOf()
        //设置适配器；加载每一行数据到列表当中
        adapter=AccountAdapter(this,mDatas)
        todayLv.adapter=adapter

        val btnedit: Button =findViewById(R.id.main_btn_edit)
        val clicklistener= View.OnClickListener { view ->
            handleClick(view)
        }
        btnedit.setOnClickListener(clicklistener)

    }

    private fun addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);

//        topbudgetTv.setOnClickListener(this);
//        headerView.setOnClickListener(this);

    }

    /*
    *设置ListView的长按事件
     */
    private fun setLVLongClickListener() {
        todayLv.setOnItemClickListener(){parent,view,position,id->
            if(position==0){
                false
            }else{
                val pos = position - 1
                val clickBean = mDatas[pos]  // 获取正在被点击的这条信息
                // 弹出提示用户选择删除还是修改的对话框
                GotoEditOrDelete(clickBean)
                false
            }
        }
    }

    private fun GotoEditOrDelete(clickbean:AccountBean) {
        val click_id=clickbean.id
        val click_name=clickbean.typename
        val click_money=clickbean.money
        val click_beizhu=clickbean.beizhu
        val click_time=clickbean.time
        val click_simageId=clickbean.simageId
        val it2=Intent(this,EditOrDeleteItem::class.java).apply {
            putExtra("BILL_ID",click_id)
            putExtra("BILL_NAME",click_name)
            putExtra("BILL_MONEY",click_money)
            putExtra("BILL_BEIZHU",click_beizhu)
            putExtra("BILL_TIME",click_time)
            putExtra("BILL_SIMAGEID",click_simageId)
        }
        startActivity(it2)
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("提示信息")
//            .setMessage("您确定要删除这条记录么？")
//            .setNegativeButton("取消", null)
//            .setPositiveButton

    }

    private fun initTime() {
        val calendar:Calendar=Calendar.getInstance()
        year=calendar.get(Calendar.YEAR)
        month=calendar.get(Calendar.MONTH)+1
        day=calendar.get(Calendar.DAY_OF_MONTH)
    }

    //当Activity获取焦点时，会调用的方法
    override fun onResume() {
        super.onResume()
        loadDBData()
    }

    private fun loadDBData() {
        var DBMins=DBManager()
        DBMins.initDB(this)
        var list:List<AccountBean> = DBMins.getAcntListFromAcnttb(year, month, day)
        mDatas.clear()
        mDatas.addAll(list)
        val totalExpenses = DBMins.getTotalExpenses(year, month, day)
        val totalIncomes = DBMins.getTotalIncomes(year, month, day)
        updateTotalsOnUI(totalExpenses, totalIncomes)
        adapter.notifyDataSetChanged()
    }

    private fun updateTotalsOnUI(totalExpenses: Float, totalIncomes: Float) {
        // 假设你有两个TextView来显示总支出和总收入
        topOutTv.text = "总支出: ￥${totalExpenses.toString()}"
        topInTv.text = "总收入: ￥${totalIncomes.toString()}"
    }

    private fun handleClick(view: View){
        when(view.id){
            R.id.main_btn_edit ->{
                val it1=Intent(this,RecordActivity::class.java)
                startActivity(it1)
            }
        }
    }
}

