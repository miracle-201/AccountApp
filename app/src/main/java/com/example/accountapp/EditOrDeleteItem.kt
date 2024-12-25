package com.example.accountapp

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.accountapp.db.DBManager

class EditOrDeleteItem : Activity() {
    private lateinit var iv2_back:ImageButton
    private lateinit var savebtn:Button
    private lateinit var deletebtn:Button
    private lateinit var typeIv:ImageView
    private lateinit var typeTv:TextView
    private lateinit var beizhuET:EditText
    private lateinit var moneyET:EditText
    private lateinit var timeTv:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_or_delete_item)

        val click_Id = intent.getIntExtra("BILL_ID",0)
        val click_name = intent.getStringExtra("BILL_NAME")
        val click_money = intent.getFloatExtra("BILL_MONEY", 0.0F)
        val click_beizhu = intent.getStringExtra("BILL_BEIZHU")
        val click_time = intent.getStringExtra("BILL_TIME")
        val click_simageId = intent.getIntExtra("BILL_SIMAGEID",0)

        initView(click_name, click_money, click_beizhu, click_time, click_simageId)
        var clicklistener= View.OnClickListener { view ->
            handleClick(view)
        }
        iv2_back.setOnClickListener(clicklistener)
        savebtn.setOnClickListener(clicklistener)
        deletebtn.setOnClickListener(clicklistener)
    }

    private fun initView(click_name: String?, click_money: Float?, click_beizhu: String?, click_time: String?, click_simageId: Int) {

        iv2_back =findViewById(R.id.record_iv2_back)
        savebtn=findViewById(R.id.frag_record_tv_save2)
        deletebtn=findViewById(R.id.frag_record_tv_delete)
        typeIv=findViewById(R.id.frag_record_iv2)
        typeTv=findViewById(R.id.frag_record_tv_type2)
        beizhuET=findViewById(R.id.frag_record_tv_beizhu2)
        moneyET=findViewById(R.id.frag_record_et_money2)
        timeTv=findViewById(R.id.frag_record_tv_time2)

        typeIv.setImageResource(click_simageId)
        typeTv.setText(click_name)
        moneyET.setText("$click_money")
        beizhuET.setText(click_beizhu)
        timeTv.setText(click_time)
    }

    private fun handleClick(view: View) {
        when(view.id){
            //左上角x返回
            R.id.record_iv2_back ->{
                finish()
            }
            //点击保存 修改数据并保存
            R.id.frag_record_tv_save2-> {
                var DBMins=DBManager()
                DBMins.initDB(this)
                val click_Id = intent.getIntExtra("BILL_ID",0)
                val moneyNew=moneyET.text.toString()
                val beizhuNew:String=beizhuET.text.toString()
                DBMins.changeItemFromAccounttcById(click_Id,moneyNew,beizhuNew)
                finish()
            }
            //点击删除
            R.id.frag_record_tv_delete->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("提示信息")
                    .setMessage("您确定要删除或修改这条记录吗？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认") { dialog, which ->
                        // 执行删除操作
                        var DBMins=DBManager()
                        DBMins.initDB(this)
                        val click_Id = intent.getIntExtra("BILL_ID",0)
                        DBMins.deleteItemFromAccounttbById(click_Id)
                        finish()
                    }
                builder.show()
            }
        }

    }
}