package com.example.myconfidence.roompersistence

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {

    val allMessages: Flow<List<MotivationalMessage>> = messageDao.getAlphabetizedMessages()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMessage(message: MotivationalMessage) {
        messageDao.insert(message)
    }
}