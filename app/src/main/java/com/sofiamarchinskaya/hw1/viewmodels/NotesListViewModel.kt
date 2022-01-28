package com.sofiamarchinskaya.hw1.viewmodels

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofiamarchinskaya.hw1.DownloadCallback
import com.sofiamarchinskaya.hw1.SingleLiveEvent
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.*
import com.sofiamarchinskaya.hw1.view.instruments.ItemsFilter
import com.sofiamarchinskaya.hw1.view.instruments.QueryFilter
import kotlinx.coroutines.launch

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var clickedNote: Note? = null

    val onLoadSuccessEvent = SingleLiveEvent<Note?>()
    val onLoadFailureEvent = SingleLiveEvent<ExceptionTypes?>()
    val onShowProgressBarEvent = SingleLiveEvent<Unit>()
    val onHideProgressBarEvent = SingleLiveEvent<Unit>()

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

    fun getNotesFromCloud() {
        onShowProgressBarEvent.call()
        repository.getAllFromCloud(object : DownloadCallback {
            override fun onSuccess(list: List<Note>) {
                list.forEach {
                    viewModelScope.launch {
                        repository.insert(it)
                    }
                }
                onHideProgressBarEvent.call()
                onLoadSuccessEvent.call()
            }

            override fun onFailed(msg: ExceptionTypes) {
                onHideProgressBarEvent.call()
                onLoadFailureEvent.value = msg
            }
        })
    }

    fun filter(query: String): List<Note>? =
        ItemsFilter().filter(query, list.value)
}