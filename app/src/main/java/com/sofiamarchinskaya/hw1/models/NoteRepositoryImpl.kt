package com.sofiamarchinskaya.hw1.models

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.DownloadCallback
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class NoteRepositoryImpl : NoteRepository {
    private val noteDao = AppDatabase.getDataBase().noteDao()
    private val fireBase = Firebase.database.reference
    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override fun getAll(): Flow<List<Note>> {
        return noteDao.getAll()
    }

    override fun getAllFromCloud(callback: DownloadCallback) {
        fireBase.child(Constants.FIREBASE_NAME).get().addOnCompleteListener { task ->
            try {
                task.result?.children?.map {
                    it.getValue(Note::class.java) ?: Note()
                }?.let { it1 ->
                    callback.onSuccess(
                        it1
                    )
                }
            } catch (e: Exception) {
                callback.onFailed("Что-то пошло не так\n Проверьте подключение к Интернету")
            }
        }
    }

    override fun insertCloud(note: Note) {
        fireBase.child(Constants.FIREBASE_NAME).child(note.id.toString()).setValue(note)
    }

    override suspend fun getLast(): Long {
        return noteDao.getLast()
    }
}

