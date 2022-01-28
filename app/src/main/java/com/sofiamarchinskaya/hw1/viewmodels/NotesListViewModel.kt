package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofiamarchinskaya.hw1.DownloadCallback
import com.sofiamarchinskaya.hw1.SingleLiveEvent
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.*
import kotlinx.coroutines.launch

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var clickedNote: Note? = null

    val onFabClickEvent = SingleLiveEvent<Unit>()
    val onNoteItemClickEvent = SingleLiveEvent<Note>()
    val list = MutableLiveData<List<Note>>()
    val contextMenuState = MutableLiveData<String>()
    var downloadState = MutableLiveData<DownloadState>()

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
        downloadState.value = DownloadState(DownloadStates.DOWNLOAD)
        repository.getAllFromCloud(object : DownloadCallback {
            override fun onSuccess(list: List<Note>) {
                list.forEach {
                    viewModelScope.launch {
                        repository.insert(it)
                    }
                }
                downloadState.value = DownloadState(DownloadStates.SUCCESS)
                downloadState.value = DownloadState((DownloadStates.FINISH))
            }

            override fun onFailed(msg: ExceptionTypes) {
                downloadState.value = DownloadState(DownloadStates.FAILED, msg)
                downloadState.value = DownloadState((DownloadStates.FINISH))
            }
        })
    }
}