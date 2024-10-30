package com.example.gmail

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mails = mutableListOf<mailModel>()
        mails.add(mailModel("Tung","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Nguyen","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Dinh","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Anh","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Dao","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Quan","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Thi","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Van","Thu dau tien","Xin chao ban","24 Th10"))
        mails.add(mailModel("Tung","Thu dau tien","Xin chao ban","24 Th10"))

        val adapter = mailAdapter(mails)
        val listmail = findViewById<ListView>(R.id.listMail)
        listmail.adapter =adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}