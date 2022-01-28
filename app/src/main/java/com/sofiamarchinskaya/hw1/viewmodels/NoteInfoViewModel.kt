package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.NoteCallback
import com.sofiamarchinskaya.hw1.SingleLiveEvent
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.*

class NoteInfoViewModel(private val repository: NoteRepository) : ViewModel() {
    val onSaveSuccessEvent = SingleLiveEvent<Unit>()
    val onSaveFailureEvent = SingleLiveEvent<Unit>()
    val onSaveAllowedEvent = SingleLiveEvent<Unit>()
    val note = MutableLiveData<Note>()
    var isNewNote = false
    val noteFromJson = MutableLiveData<JsonLoadingState>()

    suspend fun onSaveNote(isSavingToCloud: Boolean) {
        note.value?.let {
            if (it.id != Constants.INVALID_ID) {
                repository.insert(it)
                if (isSavingToCloud)
                    repository.insertCloud(it)
            } else {
                repository.insert(Note(title = it.title, body = it.body)).also { newId ->
                    if (isSavingToCloud)
                        repository.insertCloud(Note(newId, it.title, it.body))
                    note.value = Note(newId,it.title,it.body)
                }
            }
            savingState.value = SavingState(States.SAVED)
        }
        savingState.value = SavingState(States.NOTHING)
    }

    fun checkNote() =
        if (note.value?.title?.isBlank() == true || note.value?.body?.isBlank() == true) {
            onSaveFailureEvent.call()
        } else {
            onSaveAllowedEvent.call()
        }
    }

    fun getJsonNote() {
        noteFromJson.value = JsonLoadingState(JsonLoadingStates.LOADING)
        repository.loadNoteJson(object : NoteCallback {
            override fun onSuccess(note: Note?) {
                this@NoteInfoViewModel.note.value = note
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.SUCCESS)
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.FINISH)
            }

            override fun onFailed() {
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.FAILED)
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.FINISH)
            }
        })
    }

    fun setNoteTitle(title: String) {
        note.value?.title = title
    }

    fun setNoteText(text: String) {
        note.value?.body = text
    }
}

