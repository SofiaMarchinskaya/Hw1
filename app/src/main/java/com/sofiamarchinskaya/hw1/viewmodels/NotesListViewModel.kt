package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofiamarchinskaya.hw1.DownloadCallback
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.DownloadState
import com.sofiamarchinskaya.hw1.states.DownloadStates
import com.sofiamarchinskaya.hw1.states.FabState
import com.sofiamarchinskaya.hw1.states.FabStates
import kotlinx.coroutines.launch

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var clickedNote: Note? = null

    val list = MutableLiveData<List<Note>>()
    val fabState = MutableLiveData<FabState>()
    val contextMenuState = MutableLiveData<String>()
    var downloadState = MutableLiveData<DownloadState>()
    val listItemState = MutableLiveData<Note>()

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
        listItemState.value = note
    }

    fun onShareContextItemClick() {
        contextMenuState.value = clickedNote?.title + "\n" + clickedNote?.body
    }

    fun getNotesFromCloud() {
            repository.getAllFromCloud(object : DownloadCallback{
                override fun onSuccess(list: List<Note>) {
                    list.forEach {
                        viewModelScope.launch {
                            repository.insert(it)
                        }
                    }
                    downloadState.value = DownloadState(DownloadStates.SUCCESS)
                }

                override fun onFailed(msg: String) {
                    downloadState.value = DownloadState(DownloadStates.FAILED,msg)
                }

            })
    }
}