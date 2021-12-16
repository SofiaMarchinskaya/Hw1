package com.sofiamarchinskaya.hw1.presenters

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.models.Note
import com.sofiamarchinskaya.hw1.view.framework.NotesListView
import com.sofiamarchinskaya.hw1.models.NotesModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesListPresenter

class NotesListPresenterImpl(
    private var view: NotesListView?,
    private val model: NotesModel,
) : NotesListPresenter {
    private var clickedNote: Note? = null

    override fun init() {
        view?.initAdapter(model.list)
    }

    override fun onItemClick(note: Note) {
        view?.openAboutItemActivity(note)
    }

    override fun createMenu(menu: ContextMenu?) {
        view?.onMenuCreated(menu)
    }

    override fun longClick(note: Note) {
        clickedNote = note
    }

    override fun onShare() {
        view?.onShare(getDataToExtra())
    }

    override fun onDestroyView() {
        view = null
    }

    private fun getDataToExtra(): String =
        clickedNote?.title + "\n" + clickedNote?.text

}