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
    var noteId = Constants.INVALID_ID
    var isNewNote = false

    suspend fun onSaveNote(title: String, text: String) {
        with(savingState) {
            if (noteId == Constants.INVALID_ID) {
                repository.insert(Note(title = title, body = text))
            } else {
                repository.insert(Note(noteId, title, text))
            }
            value = SavingState(States.SAVED)
            value = SavingState(States.NOTHING)
        }
    }

    fun checkNote(title: String, text: String) {
        with(savingState) {
            if (title.isBlank() || text.isBlank()) {
                value = SavingState(States.ERROR)
                value = SavingState(States.NOTHING)
            } else {
                value = SavingState(States.ALLOWED)
            }
        }
    }
}
