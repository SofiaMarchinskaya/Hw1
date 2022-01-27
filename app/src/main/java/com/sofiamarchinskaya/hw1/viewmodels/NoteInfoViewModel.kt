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
    val note = MutableLiveData<Note>()
    var isNewNote = false
    val noteFromJson = MutableLiveData<JsonLoadingState>()

    suspend fun onSaveNote(isSavingToCloud: Boolean) {
        if (note.value?.id!=Constants.INVALID_ID) {
            note.value?.let { repository.insert(it) }
            if (isSavingToCloud)
                note.value?.let { repository.insertCloud(it) }
        } else {
            repository.insert(Note(title = note.value.title, body = note.value.body)).also {
                if (isSavingToCloud)
                    repository.insertCloud(Note(it,note.value.title , note.value.body))
            }
        }
        savingState.value = SavingState(States.SAVED)
        savingState.value = SavingState(States.NOTHING)
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

