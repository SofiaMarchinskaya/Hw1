package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.models.ListWithIndex
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository

class NotesPagerViewModel(private val repository: NoteRepository) : ViewModel() {
    val listWithIndex = MutableLiveData<ListWithIndex>()

    suspend fun init(id: Int?) =
        repository.getAll().collect {
            listWithIndex.value = ListWithIndex(it, getIndex(id, it))
        }

    private fun getIndex(id: Int?, list: List<Note>) =
        list.indexOfFirst { it.id == id }
}