package com.sofiamarchinskaya.hw1.models.noteApi

import com.sofiamarchinskaya.hw1.models.entity.Note
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("file1.json?alt=media")
    fun getNote(): Call<Note>
}