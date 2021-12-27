package com.sofiamarchinskaya.hw1.models.framework

import com.sofiamarchinskaya.hw1.models.entity.Note

interface NoteModel {

    suspend fun insert(note: Note): Long

    suspend fun update(note: Note)

    suspend fun getAll(): List<Note>
}