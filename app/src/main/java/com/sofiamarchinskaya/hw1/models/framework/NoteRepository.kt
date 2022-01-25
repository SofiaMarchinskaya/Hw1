package com.sofiamarchinskaya.hw1.models.framework

import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    fun getAll(): Flow<List<Note>>

    fun getAllFromCloud(callback: (List<Note>)->Unit)

    fun insertCloud(note: Note)

    suspend fun getLast():Long

    suspend fun count():Int
}