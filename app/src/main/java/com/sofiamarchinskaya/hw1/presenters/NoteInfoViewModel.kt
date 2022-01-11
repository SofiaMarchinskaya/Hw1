package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.SavingState
import com.sofiamarchinskaya.hw1.states.States
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteInfoViewModel : ViewModel() {
    private val repository = NoteRepositoryImpl()
    val savingState = MutableLiveData<SavingState>()
    var noteId = Constants.INVALID_ID
    var isNewNote = false

    suspend fun onSaveNote(title: String, text: String) {
        savingState.value = SavingState(States.SAVING)
        if (noteId == Constants.INVALID_ID) {
            repository.insert(Note(title = title, body = text))
        } else {
            repository.insert(Note(noteId, title, text))
        }
        savingState.value = SavingState(States.SAVED)
        savingState.value = SavingState(States.NOTHING)
    }

    fun checkNote(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            savingState.value = SavingState(States.ERROR)
            savingState.value = SavingState(States.NOTHING)
        } else {
            savingState.value = SavingState(States.ALLOWED)
        }
    }
}