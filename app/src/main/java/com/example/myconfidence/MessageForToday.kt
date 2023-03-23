package com.example.myconfidence

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MessageForToday : AppCompatActivity() {

    private lateinit var addNewMessage: EditText
    private lateinit var cancelSave: Button
    private lateinit var newReceivedMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_for_today)

        val firebaseMessagePreferences = getSharedPreferences("New_Message", Context.MODE_PRIVATE)
        val savedMessage = firebaseMessagePreferences.getString("The_Message", null)
        //Get the references to the buttons and views.
        //Get the new message display.
        newReceivedMessage = findViewById<TextView>(R.id.new_message_text)
        //Get the cancel save button.
        cancelSave = findViewById<Button>(R.id.cancel_save)
        //Get the save button.
        val saveButton = findViewById<Button>(R.id.save_message)
        //Return the saved token value if it is not null



        Log.d(TAG, "The new saved message is: $savedMessage")
        newReceivedMessage.text = savedMessage
        if (savedMessage != null) {

            Log.d(TAG, "The new saved message is: $savedMessage")
            newReceivedMessage.text = savedMessage
        } else {
            newReceivedMessage.text = getString(R.string.No_text_default)
        }

        addNewMessage = findViewById<EditText>(R.id.message_entry_field)


        cancelSave.setOnClickListener {
            finish()
        }

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