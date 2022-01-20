package com.sofiamarchinskaya.hw1.presenters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesPagerViewModel : ViewModel() {
    val list = MutableLiveData<List<Note>>()
    val index = MutableLiveData<Long>()
    private val repository = NoteRepositoryImpl()
    suspend fun init(id: Long?) {
        repository.getAll().collect {
            list.value = it
            index.value = getIndex(id)?.toLong()
        }
    }

    private fun getIndex(id: Long?) =
        list.value?.indexOfFirst { it.id == id }
}
