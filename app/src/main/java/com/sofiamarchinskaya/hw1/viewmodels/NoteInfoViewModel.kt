package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.SavingState
import com.sofiamarchinskaya.hw1.states.States

class NoteInfoViewModel(private val repository: NoteRepository) : ViewModel() {
    val savingState = MutableLiveData<SavingState>()
    val note = MutableLiveData<Note>()
    var isNewNote = false

    suspend fun onSaveNote() {
        with(savingState) {
            if (note.value?.id == Constants.INVALID_ID) {
                repository.insert(Note(title = note.value?.title, body = note.value?.body))
            } else {
                note.value?.let { repository.insert(it) }
            }
            value = SavingState(States.SAVED)
            value = SavingState(States.NOTHING)
        }
    }

    fun checkNote() {
        with(savingState) {
            if (note.value?.title?.isBlank() == true || note.value?.body?.isBlank() == true) {
                value = SavingState(States.ERROR)
                value = SavingState(States.NOTHING)
            } else {
                value = SavingState(States.ALLOWED)
            }
        }
    }
}
