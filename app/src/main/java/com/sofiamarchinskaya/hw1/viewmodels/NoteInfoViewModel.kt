package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.states.JsonLoadingState
import com.sofiamarchinskaya.hw1.states.JsonLoadingStates
import com.sofiamarchinskaya.hw1.states.SavingState
import com.sofiamarchinskaya.hw1.states.States
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteInfoViewModel(private val repository: NoteRepository) : ViewModel() {
    val savingState = MutableLiveData<SavingState>()
    var noteId = Constants.INVALID_ID
    var isNewNote = false
    val noteFromJson = MutableLiveData<JsonLoadingState>()

    suspend fun onSaveNote(title: String, text: String, isSavingToCloud: Boolean) {
        if (noteId != Constants.INVALID_ID) {
            repository.insert(Note(noteId, title, text))
            if (isSavingToCloud)
                repository.insertCloud(Note(noteId, title, text))
        } else {
            repository.insert(Note(title = title, body = text)).also {
                if (isSavingToCloud)
                    repository.insertCloud(Note(it, title, text))
            }
        }
        savingState.value = SavingState(States.SAVED)
        savingState.value = SavingState(States.NOTHING)
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

    fun getJsonNote() {
        noteFromJson.value = JsonLoadingState(JsonLoadingStates.LOADING)
        repository.loadNoteJson(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                noteFromJson.value =
                    JsonLoadingState(
                        JsonLoadingStates.SUCCESS,
                        response.body()
                    )
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.FINISH)
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.FAILED)
                noteFromJson.value = JsonLoadingState(JsonLoadingStates.FINISH)
            }
        })
    }
}

