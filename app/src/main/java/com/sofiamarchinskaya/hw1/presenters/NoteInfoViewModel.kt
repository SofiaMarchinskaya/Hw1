package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.NoteRepository
import com.sofiamarchinskaya.hw1.models.dao.NoteDao
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.states.SavingState
import com.sofiamarchinskaya.hw1.states.States
import kotlinx.coroutines.runBlocking

class NoteInfoViewModel : ViewModel() {
    private val repository = NoteRepository()
    val savingState = MutableLiveData<SavingState>()
    var noteId = Constants.INVALID_ID
    var isNewNote = false

    fun onSaveNote(title: String, text: String): Unit = runBlocking {
        savingState.value = SavingState(States.SAVING)
        if (noteId == Constants.INVALID_ID) {
            repository.insert(Note(title = title, body = text))
        } else {
            repository.update(Note(noteId, title, text))
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