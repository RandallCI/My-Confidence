package com.example.myconfidence

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myconfidence.roompersistence.MotivationalMessage

class MessageDetail : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_detail)

        val detail = intent.getParcelableExtra<MotivationalMessage>(MESSAGE_DETAIL)


        val detailView = findViewById<TextView>(R.id.textView)
        if (detail != null) {
            detailView.text = detail.motivation
        }
        val exit = findViewById<Button>(R.id.exit)
        exit.setOnClickListener {
            finish()
        }
    }


}