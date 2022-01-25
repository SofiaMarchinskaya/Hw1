package com.sofiamarchinskaya.hw1.models

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl : NoteRepository {
    private val noteDao = AppDatabase.getDataBase().noteDao()
    private val fireBase = Firebase.database.reference
    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override fun getAll(): Flow<List<Note>> {
        return noteDao.getAll()
    }

    override fun getAllFromCloud(callback: (List<Note>) -> Unit) {
        fireBase.child(Constants.FIREBASE_NAME).get().addOnCompleteListener { task ->
            task.result?.children?.map {
                it.getValue(Note::class.java) ?: Note()
            }?.let { it1 ->
                callback(
                    it1
                )
            }
        }
    }

    override fun insertCloud(note: Note) {
        fireBase.child(Constants.FIREBASE_NAME).child(note.id.toString()).setValue(note)
    }

    override suspend fun getLast(): Long {
        return noteDao.getLast()
    }

    override suspend fun count(): Int = noteDao.count()

}

