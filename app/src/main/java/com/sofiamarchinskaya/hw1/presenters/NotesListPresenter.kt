package com.sofiamarchinskaya.hw1.presenters

import android.view.ContextMenu
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.Note

interface NotesListPresenter {
    fun onItemClick(note: Note)

    fun initAdapter(recyclerView: RecyclerView)

    fun onMenuCreated(menu: ContextMenu?)

    fun getDataToExtra(pos: Int): String
}