package com.example.curency

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import kotlin.math.round

class MainActivity : AppCompatActivity(), View.OnTouchListener{

    var spin1 : String ="VND"
    var spin2 : String ="EUR"
    var num1 : Double = 0.0
    var num2 : Double = 0.0
    var state : Int = 0
    var previousText: String = ""
    val myCur= hashMapOf<String,Double>("VND" to 0.00004, "EUR" to 1.08260, "USD" to 1.0,"CNY" to 0.14040,
        "ARS" to 0.00101, "JPY" to 0.00653, "GBP" to 1.29675, "INR" to 0.01189,"BRL" to 0.17356,"CAD" to 0.71821
    )
    val df = DecimalFormat("#.#####")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val text1: EditText = findViewById<EditText>(R.id.num1)
        text1.setOnTouchListener(this)
        val text2: EditText = findViewById<EditText>(R.id.num2)
        text2.setOnTouchListener(this)

        val curlist: Array<String> = arrayOf("VND","EUR","USD","CNY","ARS","JPY","GBP","INR","BRL","CAD")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            curlist
        )
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        spinner1.adapter = adapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spin1 = curlist[p2]
                if(state==0){
                    num2=OnCur(spin1,spin2,num1)
                    text2.setText(roundToDecimals(num2,5).toString())
                }else{
                    num1=OnCur(spin2,spin1,num2)
                    text1.setText(roundToDecimals(num1,5).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        spinner2.adapter = adapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spin2 = curlist[p2]
                if(state==0){
                    num2=OnCur(spin1,spin2,num1)
                    text2.setText(roundToDecimals(num2,5).toString())
                }else{
                    num1=OnCur(spin2,spin1,num2)
                    text1.setText(roundToDecimals(num1,5).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }



        text1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                previousText=s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(state == 0){
                    if(s.toString()!="") {
                        var num: Double = 0.0
                        try {
                            num = s.toString().toDouble()
                        } catch (e: Exception) {
                            text1.setText(previousText)
                        }
                        num1 = num
                        num2 = OnCur(spin1, spin2, num1)
                        text2.setText(roundToDecimals(num2,5).toString())
                    }else{
                        text2.setText("")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        text2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                previousText=s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(state == 1){
                    if(s.toString()!="") {
                        var num: Double = 0.0
                        try {
                            num = s.toString().toDouble()
                        } catch (e: Exception) {
                            text2.setText(previousText)
                        }
                        num2 =num
                        num1=OnCur(spin2,spin1,num2)
                        text1.setText(roundToDecimals(num1,5).toString())
                    }else{
                        text1.setText("")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        val id = p0?.id
        val view1 : EditText = findViewById(R.id.num1)
        val view2 : EditText = findViewById(R.id.num2)
        if(id == R.id.num1){
            state = 0
            view1.setTypeface(null, Typeface.BOLD) // Làm đậm chữ
            view1.setTextColor(Color.BLACK)
            view2.setTypeface(null, Typeface.NORMAL) // Làm đậm chữ
            view2.setTextColor(Color.parseColor("#6B6B6B"))
        }else{
            state = 1
            view2.setTypeface(null, Typeface.BOLD) // Làm đậm chữ
            view2.setTextColor(Color.BLACK)
            view1.setTypeface(null, Typeface.NORMAL) // Làm đậm chữ
            view1.setTextColor(Color.parseColor("#6B6B6B"))
        }
        return false
    }

    fun OnCur(src: String, des: String, num: Double):Double{
        val i = myCur[src]
        val j = myCur[des]
        val z = j?.let { i?.div(it) }
        if (z != null) {
            return num*z
        }else return 0.0
    }


    fun roundToDecimals(value: Double, decimalPlaces: Int): Double {
        val factor = Math.pow(10.0, decimalPlaces.toDouble())
        return round(value * factor) / factor
    }


}