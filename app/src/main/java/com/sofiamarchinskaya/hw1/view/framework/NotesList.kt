package com.sofiamarchinskaya.hw1.view.framework

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.Note

interface NotesList {

    fun openAboutItemFragment(note: Note)

    fun onMenuCreated(menu: ContextMenu?)

    fun initAdapter(list: List<Note>)

    fun onShare(dataForExtra: String)

}

