package com.sofiamarchinskaya.hw1.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sofiamarchinskaya.hw1.Constants
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constants.TABLE_NAME)
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerializedName("title") @ColumnInfo(name = "note_title") val title: String = "",
    @SerializedName("text") @ColumnInfo(name = "note_text") val body: String = ""
) : Parcelable
