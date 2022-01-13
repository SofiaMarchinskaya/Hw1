package com.sofiamarchinskaya.hw1.presenters.framework

import com.sofiamarchinskaya.hw1.models.entity.Note

interface NotesPagerPresenter {

    fun init()
    fun onNoteSaved(id: Long)
    fun listCollected(list: List<Note>, id: Long): Int
}