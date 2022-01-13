package com.sofiamarchinskaya.hw1.presenters.framework

import com.sofiamarchinskaya.hw1.models.entity.Note

interface NotesPagerPresenter {

    fun init(id: Long, list: List<Note>)
}