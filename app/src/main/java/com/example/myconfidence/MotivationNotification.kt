package com.example.myconfidence

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val channelID = "Channel_one"
const val titleExtra = "Title_Extra"
const val messageExtra = "Message_Extra"

class MotivationNotification: BroadcastReceiver() {

    //Properties



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