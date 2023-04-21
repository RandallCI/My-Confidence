package com.example.myconfidence

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myconfidence.roompersistence.MessageViewModel
import com.example.myconfidence.roompersistence.MessageViewModelFactory
import com.example.myconfidence.roompersistence.MotivationalMessage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val TAG = "myConfidence"
const val MESSAGE_DETAIL = "Message Detail."

class MainActivity : AppCompatActivity() {

    private val newMessageActivityRequestCode = 1
    private lateinit var items: List<MotivationalMessage>

    private val messageViewModel: MessageViewModel by viewModels {
        MessageViewModelFactory((application as MessageApplication).messageRepository)
    }
    // Access a Cloud FireStore instance from your Activity
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.message_recycler_view)
        val adaptor = MessageListAdapter()
        adaptor.setMessageClickListener(object: MessageListAdapter.MessageClickListener{
            override fun onMessageClicked(position: Int) {
                goToDetailView(position)
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

        })
        messageViewModel.allMessages.observe(this) { messages ->
            messages.let {
                adaptor.submitList(it)
                items = messages
            }

        }


        recyclerView.adapter = adaptor
        recyclerView.layoutManager = LinearLayoutManager(this)



        val firebaseMessagePreferences = getSharedPreferences("New_Message", Context.MODE_PRIVATE)
        val savedMessage = firebaseMessagePreferences.getString("The_Message", null)
        Log.d(TAG, "The new saved message is: $savedMessage")
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


    fun newMessage(view: View) {
        val newMessageIntent = Intent(this@MainActivity, MessageForToday::class.java)
        this.startActivityForResult(newMessageIntent, newMessageActivityRequestCode)
    }

    fun messageSettings(view: View) {
        val settingsIntent = Intent(this, MessageSettingsView()::class.java)
        this.startActivity(settingsIntent)
    }


    //Mark: Private Methods

    private fun goToDetailView(position: Int) {
        val moreDetail = Intent(this, MessageDetail()::class.java)
        moreDetail.putExtra(MESSAGE_DETAIL, items[position])
        this.startActivity(moreDetail)
    }

    private fun setupCloudFireStore() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

}