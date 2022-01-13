package com.sofiamarchinskaya.hw1.view.framework

import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

interface NotesPagerActivityView {

    fun init(listFlow: Flow<List<Note>>)

    fun onNoteSaved(listFlow: Flow<List<Note>>, index: Long)
}