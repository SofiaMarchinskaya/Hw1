package com.sofiamarchinskaya.hw1.view.framework

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.models.entity.Note

interface NotesListView {

    fun openAboutItemActivity(note: Note)

    fun onMenuCreated(menu: ContextMenu?)

    fun initAdapter(list: List<Note>)

    fun onShare(dataForExtra: String)

    fun openAddNoteFragment()

}

