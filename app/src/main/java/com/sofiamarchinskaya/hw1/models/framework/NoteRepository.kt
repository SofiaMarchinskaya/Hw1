package com.sofiamarchinskaya.hw1.models.framework

import com.sofiamarchinskaya.hw1.types.DownloadCallback
import com.sofiamarchinskaya.hw1.types.NoteCallback
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note): Int

    fun getAll(): Flow<List<Note>>

    fun getAllFromCloud(callback: DownloadCallback)

    fun insertCloud(note: Note)

    suspend fun count(): Int

    fun loadNoteJson(callback: NoteCallback)
}