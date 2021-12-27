package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.NoteRepository
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NotesPagerViewModel(coroutineScope: CoroutineScope) : ViewModel(),
    CoroutineScope by coroutineScope {
    val list = MutableLiveData<List<Note>>()
    val index = MutableLiveData<Long>()
    private val repository = NoteRepository()
    fun update() {
        launch {
            list.value = repository.getAll()
        }
    }

    fun init(id: Long?){
        var i = 0L
        while (id != list.value?.get(i.toInt())?.id) {
            i++
            index.value = i
        }
    }
}