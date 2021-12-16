package com.sofiamarchinskaya.hw1.view.framework

import com.sofiamarchinskaya.hw1.models.Note

interface NotesPagerActivityView {
    fun init(list: List<Note>)
}