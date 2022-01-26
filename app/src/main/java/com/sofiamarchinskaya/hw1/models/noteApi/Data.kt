package com.sofiamarchinskaya.hw1.models.noteApi

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Data {

    fun getNoteData(): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build().create(Api::class.java)

    companion object {
        private const val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/notes-7f67b.appspot.com/o/"
    }
}

