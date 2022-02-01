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
    @SerializedName("id") @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("title") @ColumnInfo(name = "note_title") var title: String? = "",
    @SerializedName("body") @ColumnInfo(name = "note_text") var body: String? = ""
) : Parcelable
