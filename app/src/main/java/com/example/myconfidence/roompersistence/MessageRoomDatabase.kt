package com.example.myconfidence.roompersistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch

@Database(entities = arrayOf(MotivationalMessage::class), version = 1, exportSchema = false)
abstract class MessageRoomDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessageRoomDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context, scope: CoroutineScope): MessageRoomDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    MessageRoomDatabase::class.java,
                    "motivational_message_database")
                    .addCallback(MessageDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }

    private class MessageDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE.let { database ->
                scope.launch {
                    if (database != null) {
                        populateDatabase(database.messageDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(messageDAO: MessageDao) {
            messageDAO.deleteAll()

            val welcomeMessage = MotivationalMessage("Welcome to your messages.")
            messageDAO.insert(welcomeMessage)
        }
    }
}