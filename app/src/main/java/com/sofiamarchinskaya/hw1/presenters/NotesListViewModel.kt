package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesListViewModel : ViewModel() {
    private val repository = NoteRepositoryImpl()
    private var clickedNote: Note? = null
    val list = MutableLiveData<List<Note>>()

    suspend fun updateNotesList() {
        repository.getAll().collect { list.value = it }

    }

    fun longClick(note: Note) {
        clickedNote = note
    }

    fun getDataToExtra(): String =
        clickedNote?.title + "\n" + clickedNote?.body

    fun getNotesFromCloud(coroutineScope: CoroutineScope) {
        repository.getAllFromCloud { list1 ->
            list1.forEach {
                coroutineScope.launch {
                    repository.insert(it)
                }
            }
        }
    }
}