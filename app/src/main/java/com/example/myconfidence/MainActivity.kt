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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myconfidence.roompersistence.MessageViewModel
import com.example.myconfidence.roompersistence.MessageViewModelFactory
import com.example.myconfidence.roompersistence.MotivationalMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val TAG = "myconfidence"
private const val MESSAGE_DETAIL = "Message Detail."

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
        adaptor.setMessageClickListener(object: MessageListAdapter.MessageClickListener{
            override fun onMessageClicked(position: Int) {
                Toast.makeText(this@MainActivity, "You clicked $position", Toast.LENGTH_LONG).show()
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

        })
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





    //Mark: Private Methods

    private fun goToDetailView(message: MotivationalMessage) {
        val moreDetail = Intent(this, MessageDetail()::class.java)
        moreDetail.putExtra(MESSAGE_DETAIL, message.motivation)
        this.startActivity(moreDetail)
    }

}