package com.sofiamarchinskaya.hw1.view.framework

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

interface NotesListView {

    fun openAboutItemActivity(note: Note)

    fun onMenuCreated(menu: ContextMenu?)

    fun initAdapter(listFlow: Flow<List<Note>>)

    fun onShare(dataForExtra: String)

    fun openAddNoteFragment()

    fun update(notesFlow: Flow<List<Note>>)
}

