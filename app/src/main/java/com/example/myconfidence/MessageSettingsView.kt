package com.example.myconfidence

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myconfidence.databinding.ActivityMessageSettingsViewBinding
import java.util.Calendar
import java.util.Date

class MessageSettingsView : AppCompatActivity() {

    private lateinit var binding: ActivityMessageSettingsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_settings_view)

        binding = ActivityMessageSettingsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.setNotificationTime.setOnClickListener {
            scheduleMessageNotification()
            finish()
        }

        createMessageNotification()
    }

    //On click listeners.

    fun cancelSave(view: View) { finish() }

    private fun createMessageNotification() {
        val notificationName = "message notification channel"
        val notificationDescription = "notification description"
        val notificationImportance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelID, notificationName, notificationImportance)
        notificationChannel.description = notificationDescription

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun scheduleMessageNotification() {
        val notificationIntent = Intent(applicationContext, MotivationNotification::class.java)
        val messageTitle = "New Motivation"
        val notificationMessage = "View today's Motivation"
        notificationIntent.putExtra(titleExtra, messageTitle)
        notificationIntent.putExtra(messageExtra, notificationMessage)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, messageTitle, notificationMessage)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val messageTime = Date(time)
        val messageTimeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                title + message + time.toString()
            )
            .setPositiveButton("Okay") {_ ,_ -> }
    }

    private fun getTime(): Long {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour

        val calender = Calendar.getInstance()
        calender.set(hour, minute)

        return calender.timeInMillis
    }
}