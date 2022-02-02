package com.sofiamarchinskaya.hw1.types

import com.sofiamarchinskaya.hw1.models.entity.Note

interface NoteCallback {

    fun onSuccess(note: Note?)

    fun onFailed()
}