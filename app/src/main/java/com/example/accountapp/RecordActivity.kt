package com.example.accountapp

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.accountapp.adapter.TypeBaseAdapter
import com.example.accountapp.db.AccountBean
import com.example.accountapp.db.DBManager
import com.example.accountapp.db.TypeBean
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class RecordActivity : Activity() {
    private lateinit var moneyEt: EditText
    private lateinit var typeIv: ImageView
    private lateinit var typeGv: GridView
    private lateinit var typeTv: TextView
    private lateinit var beizhuTv: TextView
    private lateinit var timeTv: TextView
    var typeList = ArrayList<TypeBean>()
    private lateinit var adapter: TypeBaseAdapter
    private var accountBean:AccountBean=AccountBean()
    private lateinit var outlist: List<TypeBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        moneyEt = findViewById(R.id.frag_record_et_money)
        typeIv = findViewById(R.id.frag_record_iv)
        typeGv = findViewById(R.id.frag_record_gv)
        typeTv = findViewById(R.id.frag_record_tv_type)
        beizhuTv = findViewById(R.id.frag_record_tv_beizhu)
        timeTv = findViewById(R.id.frag_record_tv_time)

        accountBean.typename = "其他"
        accountBean.simageId = R.mipmap.ic_qita_fs
        var DBMins=DBManager()
        DBMins.initDB(this)
        outlist = DBMins.getTypeList(0)
        accountBean.kind=0

        val iv_back:ImageButton=findViewById(R.id.record_iv_back)
        val onsave:Button=findViewById(R.id.frag_record_tv_save)
        val btnout:Button=findViewById(R.id.record_btnout)
        val btnin:Button=findViewById(R.id.record_btnin)
        var clicklistener= View.OnClickListener { view ->
            handleClick(view)
        }
        iv_back.setOnClickListener(clicklistener)
        onsave.setOnClickListener(clicklistener)
        btnout.setOnClickListener(clicklistener)
        btnin.setOnClickListener(clicklistener)

        loadDataToGV() //给GridView填充数据
        setGVListener()
        setInitTime()
    }
    /*获取当前时间，显示在timeTv上*/
    private fun setInitTime() {
        val date:Date=Date()
        val sdf:SimpleDateFormat=SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        val time:String=sdf.format(date)
        timeTv.setText(time)
        accountBean.time=time
        val calendar:Calendar=Calendar.getInstance()
        var year:Int=calendar.get(Calendar.YEAR)
        var month:Int=calendar.get(Calendar.MONTH)+1
        var day:Int=calendar.get(Calendar.DAY_OF_MONTH)
        accountBean.year = year
        accountBean.month = month
        accountBean.day = day
    }

    /*给GridView填充数据的方法*/
    private fun loadDataToGV(){
        adapter = TypeBaseAdapter(this, typeList)
        typeGv.adapter = adapter // 设置适配器
        // 获取数据库中的数据源
        typeList.addAll(outlist)
        adapter.notifyDataSetChanged() // 通知适配器数据已更改
    }
    /*设置GridView每一项的点击事件*/
    private fun setGVListener(){
        typeGv.setOnItemClickListener{parent,view,position,id->
            adapter.selectPos=position
            adapter.notifyDataSetInvalidated() //提示绘制发生变化了
            val typeBean = typeList[position]
            val typename = typeBean.typename
            typeTv.text=typename
            accountBean.typename=typename
            val simageId = typeBean.simageId
            typeIv.setImageResource(simageId)
            accountBean.simageId = simageId
        }
    }
    private fun handleClick(view:View){
        when(view.id){
            //左上角x返回
            R.id.record_iv_back ->{
                finish()
            }
            R.id.record_btnout->{
                var DBMins=DBManager()
                DBMins.initDB(this)
                typeList.clear()
                outlist = DBMins.getTypeList(0)
                accountBean.kind=0
                loadDataToGV()
            }
            R.id.record_btnin->{
                var DBMins=DBManager()
                DBMins.initDB(this)
                typeList.clear()
                outlist = DBMins.getTypeList(1)
                accountBean.kind=1
                loadDataToGV()
            }
            //点击确定按钮：获取记录信息并保存，返回上一级界面
            R.id.frag_record_tv_save->{
                //获取输入钱数和备注
                var moneyStr:String=moneyEt.text.toString()
                if(TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")){
                    finish()
                    return
                }
                var money:Float=moneyStr.toFloat()
                accountBean.money=money

                var beizhuStr:String=beizhuTv.text.toString()
                if(TextUtils.isEmpty(beizhuStr)||beizhuStr.equals("")){
                    finish()
                    return
                }
                var beizhu:String=beizhuStr.toString()
                accountBean.beizhu=beizhu

                var timeStr:String=timeTv.text.toString()
                if(TextUtils.isEmpty(timeStr)||timeStr.equals("")){
                    finish()
                    return
                }
                var time:String=timeStr.toString()
                accountBean.time=time
                //获取记录的信息，保存在数据库当中
                saveAccountToDB()
                //返回上一级界面
                finish()
            }
        }
    }

    private fun saveAccountToDB() {
        var DBMins=DBManager()
        DBMins.initDB(this)
        DBMins.insertItemToAccounttb(accountBean)
    }

}


