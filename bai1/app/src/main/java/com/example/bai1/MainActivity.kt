package com.example.bai1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val inputview = findViewById<EditText>(R.id.inputnum)
        val radgroup : RadioGroup = findViewById(R.id.radgroup)
        val rad1 : RadioButton = findViewById(R.id.radioButton1)
        val rad2 : RadioButton = findViewById(R.id.radioButton2)
        val rad3 : RadioButton = findViewById(R.id.radioButton3)
        val button : Button = findViewById(R.id.show)
        val listview: ListView = findViewById(R.id.list1)
        val errortext: TextView = findViewById(R.id.error)

        val items : ArrayList<Int> = arrayListOf()
        val adapter = ArrayAdapter<Int>(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        listview.adapter = adapter

        button.setOnClickListener{
            if(inputview.text.isEmpty()){
                errortext.setText("Bạn chưa nhập gì")
            }else{
                errortext.setText("")
                var num : Int = inputview.text.toString().toInt()
                if(num>0){
                    val radioId = radgroup.checkedRadioButtonId
                    when(radioId){
                        rad1.id -> {
                            items.clear()
                            for(i in 0 .. num step 2){
                                items.add(i)
                            }
                        }
                        rad2.id ->{
                            items.clear()
                            for(i in 1 .. num step 2){
                                items.add(i)
                            }
                        }
                        rad3.id ->{
                            items.clear()
                            for(i in 1 .. sqrt(num.toDouble()).toInt() ){
                                items.add(i*i)
                            }
                        }
                        else ->{
                            errortext.setText("Hãy chọn một mục")
                        }
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    errortext.setText("Không được nhập số âm")
                }
            }

        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}