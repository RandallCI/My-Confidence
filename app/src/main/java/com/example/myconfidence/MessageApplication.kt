package com.example.myconfidence

import android.app.Application
import com.example.myconfidence.roompersistence.MessageRepository
import com.example.myconfidence.roompersistence.MessageRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.supervisorScope

class MessageApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val messageDatabase by lazy { MessageRoomDatabase.getDatabase(this, applicationScope) }
    val messageRepository by lazy { MessageRepository(messageDatabase.messageDao()) }
}