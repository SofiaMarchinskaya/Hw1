package com.sofiamarchinskaya.hw1.models.framework

import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    fun getAll(): Flow<List<Note>>
}