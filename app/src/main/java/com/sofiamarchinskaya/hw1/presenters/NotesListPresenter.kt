package com.sofiamarchinskaya.hw1.presenters

import android.view.ContextMenu
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.Note
import com.sofiamarchinskaya.hw1.NotesAdapter

interface NotesListPresenter {

    fun init()

    fun getDataToExtra(pos: Int): String
}