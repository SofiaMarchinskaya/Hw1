package com.sofiamarchinskaya.hw1

import android.view.ContextMenu
import androidx.recyclerview.widget.RecyclerView
import com.sofiamarchinskaya.hw1.presenters.NotesListPresenter

class NotesListPresenterImpl(
    private var view: NotesList?,
    private val model: NotesModel,
) : NotesListPresenter {

    override fun onItemClick(note: Note) {
        view?.openAboutItemFragment(note)
    }

    override fun initAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter = NotesAdapter(model.list, this::onItemClick, this::onMenuCreated)
    }

    override fun onMenuCreated(menu: ContextMenu?) {
        view?.onMenuCreated(menu)
    }

    override fun getDataToExtra(pos: Int): String =
        model.list[pos].title + "\n" + model.list[pos].text


}