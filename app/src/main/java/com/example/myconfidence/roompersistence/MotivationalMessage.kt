package com.example.myconfidence.roompersistence

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motivational_message_table")
data class MotivationalMessage(
    @PrimaryKey
    @ColumnInfo(name = "motivation")
    val motivation: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(motivation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MotivationalMessage> {
        override fun createFromParcel(parcel: Parcel): MotivationalMessage {
            return MotivationalMessage(parcel)
        }

        override fun newArray(size: Int): Array<MotivationalMessage?> {
            return arrayOfNulls(size)
        }
    }
}