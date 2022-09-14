package com.example.myconfidence.roompersistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = arrayOf(Message::class), version = 1, exportSchema = false)
abstract class MessageRoomDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessageRoomDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): MessageRoomDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    MessageRoomDatabase::class.java,
                    "motivational_message_database").build()
                INSTANCE = instance
                return instance
            }

        }
    }
}