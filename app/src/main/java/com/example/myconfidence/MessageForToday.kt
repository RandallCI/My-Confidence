package com.example.myconfidence

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MessageForToday : AppCompatActivity() {

    private lateinit var addNewMessage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_for_today)

        addNewMessage = findViewById<EditText>(R.id.message_entry_field)

        val saveButton = findViewById<Button>(R.id.save_message)
        saveButton.setOnClickListener {
            val messageIntent = Intent()
            if (TextUtils.isEmpty(addNewMessage.text)) {
                setResult(Activity.RESULT_CANCELED, messageIntent)
            } else {
                val message = addNewMessage.text.toString()
                messageIntent.putExtra(MESSAGE_REPLY, message)
                setResult(Activity.RESULT_OK, messageIntent)
                finish()
            }
        }
    }

    companion object {
        const val MESSAGE_REPLY = "com.example.myconfidence"
    }
}