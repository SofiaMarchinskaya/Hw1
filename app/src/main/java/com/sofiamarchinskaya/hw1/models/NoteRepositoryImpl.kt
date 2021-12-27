package com.sofiamarchinskaya.hw1.models

import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository

class NoteRepositoryImpl : NoteRepository {
    private val noteDao = AppDatabase.getDataBase().noteDao()

    override suspend fun insert(note: Note): Long {
        return noteDao.insert(note)
    }

    override suspend fun update(note: Note) {
        return noteDao.update(note)
    }

    override suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }
}

