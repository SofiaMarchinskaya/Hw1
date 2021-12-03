package com.sofiamarchinskaya.hw1.presenters

interface NoteInfoPresenter {

    fun onSave(title: String, text: String)

    fun onDestroy()

}