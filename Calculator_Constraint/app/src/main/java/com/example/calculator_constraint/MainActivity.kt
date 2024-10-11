package com.example.calculator_constraint

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() , View.OnClickListener{
    lateinit var textReuslt: TextView
    lateinit var textLabel: TextView

    var state: Int = 1
    var op: Int = 0
    var num1: Long = 0
    var num2: Long = 0
    var sign1: Int = 1
    var sign2: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textReuslt = findViewById(R.id.textView)
        textLabel = findViewById(R.id.textView2)

        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMul).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDiv).setOnClickListener(this)
        findViewById<Button>(R.id.buttonSub).setOnClickListener(this)
        findViewById<Button>(R.id.buttonAdd).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBS).setOnClickListener(this)
        findViewById<Button>(R.id.buttonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonCE).setOnClickListener(this)
        findViewById<Button>(R.id.buttonInvert).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEqual).setOnClickListener(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        if (id == R.id.button0) {
            addDigit(0)
        } else if (id == R.id.button1) {
            addDigit(1)
        } else if (id == R.id.button2) {
            addDigit(2)
        } else if (id == R.id.button3) {
            addDigit(3)
        } else if (id == R.id.button4) {
            addDigit(4)
        } else if (id == R.id.button5) {
            addDigit(5)
        } else if (id == R.id.button6) {
            addDigit(6)
        } else if (id == R.id.button7) {
            addDigit(7)
        } else if (id == R.id.button8) {
            addDigit(8)
        } else if (id == R.id.button9) {
            addDigit(9)
        } else if (id == R.id.buttonAdd) {
            if(state == 1){
                state = 2
                op = 1
                textLabel.text = "${sign1*num1} +"
            }else{
                num1 = Result()
                textLabel.text = "$num1 +"
                textReuslt.text = "$num1"
                if(num1<0){
                    sign1 = -1
                    num1 = num1.absoluteValue
                }
                op = 1
                num2 = 0
                sign2 = 1
            }
        } else if (id == R.id.buttonSub) {
            if(state == 1){
                state = 2
                op = 2
                textLabel.text = "${sign1*num1} -"
            }else{
                num1 = Result()
                textLabel.text = "$num1 -"
                textReuslt.text = "$num1"
                if(num1<0){
                    sign1 = -1
                    num1 = num1.absoluteValue
                }
                op = 2
                num2 = 0
                sign2 = 1
            }
        } else if (id == R.id.buttonMul) {
            if(state == 1){
                state = 2
                op = 3
                textLabel.text = "${sign1*num1} *"
            }else{
                num1 = Result()
                textLabel.text = "$num1 *"
                textReuslt.text = "$num1"
                if(num1<0){
                    sign1 = -1
                    num1 = num1.absoluteValue
                }
                op = 3
                num2 = 0
                sign2 = 1
            }
        } else if (id == R.id.buttonDiv) {
            if(state == 1){
                state = 2
                op = 4
                textLabel.text = "${sign1*num1} /"
            }else{
                num1 = Result()
                textLabel.text = "$num1 /"
                textReuslt.text = "$num1"
                if(num1<0){
                    sign1 = -1
                    num1 = num1.absoluteValue
                }
                op = 4
                num2 = 0
                sign2 = 1
            }
        } else if (id == R.id.buttonBS) {
            if(state == 1){
                num1 = num1/10
                if(num1.toString().length<11) textReuslt.textSize = 60F
                textReuslt.text ="${sign1*num1}"
            }else{
                num2= num2/10
                if(num2.toString().length<11) textReuslt.textSize = 60F
                textReuslt.text ="${sign2*num2}"
            }
        } else if (id == R.id.buttonC) {
            state = 1
            op = 0
            num1 = 0
            num2 = 0
            sign1 = 1
            sign2 = 1
            textReuslt.text ="0"
            textLabel.text=""
        } else if (id == R.id.buttonCE) {
            if(state == 1){
                num1 = 0
                sign1 = 1
            }else{
                num2 = 0
                sign2 = 1
            }
            textReuslt.text = "0"
        } else if (id == R.id.buttonInvert) {
            if(state == 1){
                sign1 = -sign1
                textReuslt.text ="${sign1*num1}"
            }else{
                sign2 = -sign2
                textReuslt.text ="${sign2*num2}"
            }
        } else if (id == R.id.buttonEqual) {
            textLabel.text = "${sign1*num1} ${getStringFromOp()} ${sign2*num2} ="
            textReuslt.text = "${Result()}"
            state = 1
            op = 0
            num1 = 0
            num2 = 0
            sign1 = 1
            sign2 = 1
        }
    }

    fun addDigit(c: Int) {
        if (state == 1) {
            val length = num1.toString().length
            if(length<16){
                if(length>9){
                    textReuslt.textSize = 38F
                }else{
                    textReuslt.textSize = 60F
                }
                num1 = num1 * 10 + c
                textReuslt.text = "${sign1*num1}"
            }
        } else {
            val length = num2.toString().length
            if(length<16){
                if(length>9){
                    textReuslt.textSize = 38F
                }else{
                    textReuslt.textSize = 60F
                }
                num2 = num2 * 10 + c
                textReuslt.text = "${sign2*num2}"
            }
        }
    }

    fun Result() : Long {
        if(op == 1){
            return sign1*num1 + sign2*num2
        }else if (op == 2) {
            return sign1*num1 - sign2*num2
        }else if (op == 3) {
            return sign1*num1 * sign2*num2
        }else if (op == 4) {
            return sign1*num1 / (sign2*num2)
        }else {
            return 0
        }
    }

    fun getStringFromOp(): String{
        if(op == 1){
            return "+"
        }else if (op == 2) {
            return "-"
        }else if (op == 3) {
            return "*"
        }else if (op ==4) {
            return "/"
        }else {
            return ".."
        }
    }
}