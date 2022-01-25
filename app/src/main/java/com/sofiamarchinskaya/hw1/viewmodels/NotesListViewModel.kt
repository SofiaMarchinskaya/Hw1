package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.FabState
import com.sofiamarchinskaya.hw1.states.FabStates
import com.sofiamarchinskaya.hw1.states.ListItemState
import com.sofiamarchinskaya.hw1.states.ListItemStates

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var clickedNote: Note? = null

    val list = MutableLiveData<List<Note>>()
    val fabState = MutableLiveData<FabState>()
    val contextMenuState = MutableLiveData<String>()
    val listItemState = MutableLiveData<ListItemState>()

    suspend fun updateNotesList() {
        repository.getAll().collect { list.value = it }
    }

    fun longClick(note: Note) {
        clickedNote = note
    }

    fun onFabClicked() {
        fabState.value = FabState(FabStates.OnClicked)
        fabState.value = FabState(FabStates.NotClicked)
    }

    fun onAboutItemClicked(note: Note) {
        listItemState.value = ListItemState(ListItemStates.OnClicked,note)
        listItemState.value = ListItemState(ListItemStates.NotClicked)
    }

    fun onShareContextItemClick() {
        contextMenuState.value = clickedNote?.title + "\n" + clickedNote?.body
    }
}