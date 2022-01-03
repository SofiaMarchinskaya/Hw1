package com.sofiamarchinskaya.hw1.models

import com.google.gson.annotations.SerializedName
import com.sofiamarchinskaya.hw1.models.entity.Note

data class Notes(
    @SerializedName("notes") val notes : List<Note>
)