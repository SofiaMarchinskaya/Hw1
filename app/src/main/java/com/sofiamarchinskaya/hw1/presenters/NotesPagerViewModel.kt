package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NotesPagerViewModel : ViewModel() {
    val list = MutableLiveData<List<Note>>()
    val index = MutableLiveData<Long>()
    private lateinit var coroutineScope: CoroutineScope
    private val repository = NoteRepositoryImpl()
    fun update() {
        coroutineScope.launch {
            list.value = repository.getAll()
        }
    }

    fun init(id: Long?) {
        var i = 0L
        while (id != list.value?.get(i.toInt())?.id) {
            i++
            index.value = i
        }
    }

    fun setCoroutineScope(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
    }
}