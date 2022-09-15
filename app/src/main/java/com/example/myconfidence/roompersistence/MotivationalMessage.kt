package com.example.myconfidence.roompersistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motivational_message_table")
data class MotivationalMessage(
    @PrimaryKey
    @ColumnInfo(name = "motivation")
    val motivation: String
)