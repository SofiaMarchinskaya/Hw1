package com.sofiamarchinskaya.hw1.models

import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl : NoteRepository {
    private val noteDao = AppDatabase.getDataBase().noteDao()

    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override fun getAll(): Flow<List<Note>> {
        return noteDao.getAll()
    }
}

