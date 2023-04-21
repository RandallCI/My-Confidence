package com.example.myconfidence

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MessageSettingsView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_settings_view)
    }

    //On click listeners.

    fun setTime(view: View) {}

    fun cancelSave(view: View) { finish() }
}