package com.sofiamarchinskaya.hw1.presenters.framework

interface NoteInfoPresenter {
   fun onSaveNote(title: String, text: String, id: Long)
}