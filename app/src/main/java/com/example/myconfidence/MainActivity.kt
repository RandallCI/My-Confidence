package com.example.myconfidence

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myconfidence.roompersistence.MessageViewModel
import com.example.myconfidence.roompersistence.MessageViewModelFactory
import com.example.myconfidence.roompersistence.MotivationalMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG = "myconfidence"

class MainActivity : AppCompatActivity() {

    private val newMessageActivityRequestCode = 1

    private val messageViewModel: MessageViewModel by viewModels {
        MessageViewModelFactory((application as MessageApplication).messageRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.message_recycler_view)
        val adaptor = MessageListAdapter()
        messageViewModel.allMessages.observe(this, Observer { messages ->
            messages.let { adaptor.submitList(it) }
        })

        recyclerView.adapter = adaptor
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val newMessageIntent = Intent(this@MainActivity, MessageForToday::class.java)
            startActivityForResult(newMessageIntent, newMessageActivityRequestCode)


        }

    getTokenForFirebaseMessaging()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newMessageActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(MessageForToday.MESSAGE_REPLY)?.let {
                val theMessage = MotivationalMessage(it)
                messageViewModel.insert(theMessage)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    private fun getTokenForFirebaseMessaging() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { regTokenTask ->
            if (regTokenTask.isSuccessful) {
                Log.d(TAG, "FCM registration token: ${regTokenTask.result}")
                //Set a save location.
                val firebaseTokenPreferences = getSharedPreferences("Firebase_Token", Context.MODE_PRIVATE)
                val editToken = firebaseTokenPreferences.edit()
                //Get the value to be saved.
                val token = regTokenTask.result
                editToken.apply {
                    putString("Token", token)
                    apply()
                }
                //Return the saved token value if it is not null
                val savedToken = firebaseTokenPreferences.getString("Token", null)
                if (savedToken != null) {
                    Log.d(TAG, "The saved token value is: ${savedToken}")
                }
            } else {
                Log.e(
                    TAG, "Unable to retrieve registration token",
                    regTokenTask.exception)
            }
        }
        FirebaseInstallations.getInstance().id.addOnCompleteListener { installationIdTask ->
            if (installationIdTask.isSuccessful) {
                Log.d(TAG, "Firebase Installations ID: ${installationIdTask.result}")

            } else {
                Log.e(
                    TAG, "Unable to retrieve installations ID",
                    installationIdTask.exception)
            }
        }

    }
}