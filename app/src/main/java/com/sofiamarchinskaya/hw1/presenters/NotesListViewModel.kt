package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.NoteRepository
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.runBlocking

class NotesListViewModel : ViewModel() {
    private val repository = NoteRepository()
    private var clickedNote: Note? = null

    val list = MutableLiveData<List<Note>>()

    fun updateNotesList(): Unit = runBlocking {
        list.value = repository.getAll()
    }

    fun longClick(note: Note) {
        clickedNote = note
    }

    fun getDataToExtra(): String =
        clickedNote?.title + "\n" + clickedNote?.body
}