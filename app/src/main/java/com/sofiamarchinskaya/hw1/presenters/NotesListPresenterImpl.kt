package com.sofiamarchinskaya.hw1.presenters

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.Note
import com.sofiamarchinskaya.hw1.view.framework.NotesList
import com.sofiamarchinskaya.hw1.models.NotesModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesListPresenter

class NotesListPresenterImpl(
    private var view: NotesList?,
    private val model: NotesModel,
) : NotesListPresenter {
    private var clickedNote: Note? = null

    override fun init() {
        view?.initAdapter(model.list)
    }

    private fun getDataToExtra(): String =
        clickedNote?.title + "\n" + clickedNote?.text

    override fun onItemClick(note: Note) {
        view?.openAboutItemFragment(note)
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

}