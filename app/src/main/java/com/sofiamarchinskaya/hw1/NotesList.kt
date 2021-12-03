package com.sofiamarchinskaya.hw1

import android.view.ContextMenu

interface NotesList {

    fun openAboutItemFragment(note: Note)

    fun onMenuCreated(menu: ContextMenu?)
}

