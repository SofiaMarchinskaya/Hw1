package com.sofiamarchinskaya.hw1.models.framework

import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteModel {

    suspend fun insert(note: Note): Long

    fun getAll(): Flow<List<Note>>
}