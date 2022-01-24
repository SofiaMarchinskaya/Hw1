package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.DownloadState
import com.sofiamarchinskaya.hw1.states.DownloadStates
import com.sofiamarchinskaya.hw1.states.FabState
import com.sofiamarchinskaya.hw1.states.FabStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var clickedNote: Note? = null

    val list = MutableLiveData<List<Note>>()
    val fabState = MutableLiveData<FabState>()
    val contextMenuState = MutableLiveData<String>()
    val downloadState = MutableLiveData<DownloadState>()

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

    fun selectContextMenuItem(itemId: Int) {
        when (itemId) {
            R.id.share -> {
                contextMenuState.value = clickedNote?.title + "\n" + clickedNote?.body
            }
        }
    }

    fun getNotesFromCloud() {
        try {
            downloadState.value = DownloadState(DownloadStates.RUNNING)
            repository.getAllFromCloud { list1 ->
                list1.forEach {
                    viewModelScope.launch {
                        repository.insert(it)
                    }
                }
                downloadState.value = DownloadState(DownloadStates.SUCCESS)
            }
        } catch (e: Exception) {
            downloadState.value = DownloadState(DownloadStates.FAILED)
        }
    }
}