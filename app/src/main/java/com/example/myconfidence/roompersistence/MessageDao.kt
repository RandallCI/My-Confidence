package com.example.myconfidence.roompersistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM motivational_message_table ORDER BY motivation")
    fun getAlphabetizedMessages(): Flow<List<MotivationalMessage>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: MotivationalMessage)

    @Query("DELETE FROM motivational_message_table")
    suspend fun deleteAll()
}