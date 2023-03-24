package com.example.myconfidence

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MessageDetail : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_detail)

        val detail = intent.getStringExtra(MESSAGE_DETAIL)
        val detailView = findViewById<TextView>(R.id.textView)
        detailView.text = detail
        val exit = findViewById<Button>(R.id.exit)
        exit.setOnClickListener {
           finish()
        }
    }


}