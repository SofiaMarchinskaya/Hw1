package com.sofiamarchinskaya.hw1.presenters.framework

interface NoteInfoPresenter {

    suspend fun onSaveNote(title: String, text: String, id: Long)

    fun checkNote(title: String, text: String)

    fun onDestroy()
}