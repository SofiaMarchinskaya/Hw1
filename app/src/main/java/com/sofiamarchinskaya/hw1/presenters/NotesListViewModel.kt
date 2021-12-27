package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NotesListViewModel : ViewModel() {
    private lateinit var coroutineScope: CoroutineScope
    private val repository = NoteRepositoryImpl()
    private var clickedNote: Note? = null

    val list = MutableLiveData<List<Note>>()

    fun updateNotesList() {
        coroutineScope.launch {
            list.value = repository.getAll()
        }
    }

    fun longClick(note: Note) {
        clickedNote = note
    }

    fun getDataToExtra(): String =
        clickedNote?.title + "\n" + clickedNote?.body

    fun setCoroutineScope(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
    }
}