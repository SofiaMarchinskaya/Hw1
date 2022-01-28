package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.SingleLiveEvent
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var clickedNote: Note? = null

    val onFabClickEvent = SingleLiveEvent<Unit>()
    val onNoteItemClickEvent = SingleLiveEvent<Note>()


    val list = MutableLiveData<List<Note>>()
    val contextMenuState = MutableLiveData<String>()

    suspend fun updateNotesList() {
        repository.getAll().collect { list.value = it }
    }

    fun longClick(note: Note) {
        clickedNote = note
    }

    fun onFabClicked() {
        onFabClickEvent.call()
    }

    fun onAboutItemClicked(note: Note) {
        onNoteItemClickEvent.value = note
    }

    fun onShareContextItemClick() {
        contextMenuState.value = clickedNote?.title + "\n" + clickedNote?.body
    }
}