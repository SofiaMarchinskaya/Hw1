package com.sofiamarchinskaya.hw1.presenters.framework

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.models.entity.Note

interface NotesListPresenter {

    fun init()

    fun onItemClick(note: Note)

    fun createMenu(menu: ContextMenu?)

    fun longClick(note: Note)

    fun onShare()

    fun onDestroyView()

    fun addNote()

    fun onResume()
}