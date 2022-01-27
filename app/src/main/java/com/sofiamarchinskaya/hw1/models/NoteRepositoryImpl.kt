package com.sofiamarchinskaya.hw1.models

import com.google.firebase.FirebaseException
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.DownloadCallback
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.models.noteApi.Data
import com.sofiamarchinskaya.hw1.states.ExceptionTypes
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback

class NoteRepositoryImpl : NoteRepository {
    private val noteDao = AppDatabase.getDataBase().noteDao()
    private val fireBase = Firebase.database.reference

    override suspend fun insert(note: Note): Int =
        noteDao.insert(note).toInt()

    override fun getAll(): Flow<List<Note>> {
        return noteDao.getAll()
    }

    override fun getAllFromCloud(callback: DownloadCallback) {
        fireBase.child(Constants.FIREBASE_NAME).get().addOnCompleteListener { task ->
            try {
                task.result?.children?.map {
                    it.getValue(Note::class.java) ?: Note()
                }?.let { list ->
                    callback.onSuccess(
                        list
                    )
                }
            } catch (e: Exception) {
                callback.onFailed(ExceptionTypes.CLIENT_IS_OFFLINE)
            } catch (fe: FirebaseException) {
                callback.onFailed(ExceptionTypes.FIREBASE_EXC)
            }
        }
    }

    override fun insertCloud(note: Note) {
        fireBase.child(Constants.FIREBASE_NAME).child(note.id.toString()).setValue(note)
    }

    override fun loadNoteJson(callback: Callback<Note>) {
        Data().getNoteData().getNote().enqueue(callback)
    }

    override suspend fun count(): Int = noteDao.count()

}

