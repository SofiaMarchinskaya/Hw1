package com.sofiamarchinskaya.hw1

import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.ExceptionTypes

interface NoteCallback {

    fun onSuccess(note: Note?)

    fun onFailed()
}