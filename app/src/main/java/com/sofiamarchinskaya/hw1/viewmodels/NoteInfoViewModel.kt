package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.SingleLiveEvent
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository

class NoteInfoViewModel(private val repository: NoteRepository) : ViewModel() {
    val onSaveSuccessEvent = SingleLiveEvent<Unit>()
    val onSaveFailureEvent = SingleLiveEvent<Unit>()
    val onSaveAllowedEvent = SingleLiveEvent<Unit>()
    val note = MutableLiveData<Note>()
    var isNewNote = false

    suspend fun onSaveNote() {
        if (note.value?.id == Constants.INVALID_ID) {
            repository.insert(Note(title = note.value?.title, body = note.value?.body))
        } else {
            note.value?.let { repository.insert(it) }
        }
        onSaveSuccessEvent.call()
    }

    fun checkNote() =
        if (note.value?.title?.isBlank() == true || note.value?.body?.isBlank() == true) {
            onSaveFailureEvent.call()
        } else {
            onSaveAllowedEvent.call()
        }
}


