package com.sofiamarchinskaya.hw1.models

import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note

class NoteRepository {
    private val noteDao = AppDatabase.getDataBase().noteDao()
    suspend fun insert(note: Note): Long {
        return noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        return noteDao.update(note)
    }

    suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }
}

