package com.example.myconfidence

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: message is " + message.data.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        //Set a save location.
        val firebaseTokenPreferences = getSharedPreferences("Firebase_Token", Context.MODE_PRIVATE)
        val editToken = firebaseTokenPreferences.edit()
        //Get the value to be saved.
        editToken.apply {
            putString("Token", token)
            apply()
        }

        //Return the saved token value if it is not null
        val savedToken = firebaseTokenPreferences.getString("Token", null)
        if (savedToken != null) {
            Log.d(TAG, "The new saved token value is: $savedToken")
        }
    }

}