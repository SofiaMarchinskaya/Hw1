package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.types.Constants
import com.sofiamarchinskaya.hw1.types.NoteCallback
import com.sofiamarchinskaya.hw1.utils.SingleLiveEvent
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository

class NoteInfoViewModel(private val repository: NoteRepository) : ViewModel() {
    val onSaveSuccessEvent = SingleLiveEvent<Note?>()
    val onSaveFailureEvent = SingleLiveEvent<Unit>()
    val onSaveAllowedEvent = SingleLiveEvent<Unit>()
    val onLoadSuccessEvent = SingleLiveEvent<Note?>()
    val onLoadFailureEvent = SingleLiveEvent<Unit>()
    val onShowProgressBarEvent = SingleLiveEvent<Unit>()
    val onHideProgressBarEvent = SingleLiveEvent<Unit>()
    val onLoadLocationClickEvent = SingleLiveEvent<List<String>>()

    val note = MutableLiveData<Note>()
    var isNewNote = false

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
                    note.value = Note(newId, it.title, it.body)
                }
            }
            onSaveSuccessEvent.setValue(note.value)
            onSaveSuccessEvent.call()
        }
    }

    fun checkNote() {
        if (note.value?.title?.isBlank() == true || note.value?.body?.isBlank() == true) {
            onSaveFailureEvent.call()
        } else {
            onSaveAllowedEvent.call()
        }
    }

    fun getJsonNote() {
        onShowProgressBarEvent.call()
        repository.loadNoteJson(object : NoteCallback {
            override fun onSuccess(note: Note?) {
                onLoadSuccessEvent.value = note
                onHideProgressBarEvent.call()
                onLoadSuccessEvent.call()
            }

            override fun onFailed() {
                onHideProgressBarEvent.call()
                onLoadFailureEvent.call()
            }
        })
    }

    fun setNoteTitle(title: String) {
        note.value?.title = title
    }

    fun setNoteText(text: String) {
        note.value?.body = text
    }

    fun onLocationItemClick() {
        onLoadLocationClickEvent.call()
    }
}

