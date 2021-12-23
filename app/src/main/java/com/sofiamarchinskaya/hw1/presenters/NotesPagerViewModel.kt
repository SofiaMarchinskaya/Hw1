package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.NoteRepository
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.runBlocking

class NotesPagerViewModel : ViewModel() {
    val list = MutableLiveData<List<Note>>()
    val index = MutableLiveData<Long>()
    private val repository = NoteRepository()
    fun update(): Unit = runBlocking {
        list.value = repository.getAll()
    }

    fun init(id: Long?): Unit = runBlocking {
        var i = 0L
        while (id != list.value?.get(i.toInt())?.id) {
            i++
            index.value = i
        }
    }
}