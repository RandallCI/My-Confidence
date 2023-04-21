package com.example.myconfidence

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class MotivationNotification: BroadcastReceiver() {

    //Properties
    private val notificationID = 1
    private val channelID = "Channel_one"
    private val titleExtra = "Title_Extra"
    private val messageExtra = "Message_Extra"


    override fun onReceive(p0: Context, p1: Intent?) {
        val messageNotification = NotificationCompat.Builder(p0, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(p1?.getStringExtra(titleExtra))
            .setContentText(p1?.getStringExtra(messageExtra))
            .build()

        val notificationManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationID, messageNotification)
    }

}