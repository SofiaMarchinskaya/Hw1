package com.sofiamarchinskaya.hw1.presenters

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesListPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesListView

class NotesListPresenterImpl(
    private val model: NoteModel,
    private var view: NotesListView?,
) : NotesListPresenter {
    private var clickedNote: Note? = null

    override fun init() {
        view?.initAdapter(model.getAll())
    }

    override fun onItemClick(note: Note) {
        view?.openAboutItemActivity(note)
    }

    override fun createMenu(menu: ContextMenu?) {
        view?.onMenuCreated(menu)
    }

    override fun onResume() {
        view?.update(model.getAll())
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

    override fun addNote() {
        view?.openAddNoteFragment()
    }

    private fun getDataToExtra(): String =
        clickedNote?.title + "\n" + clickedNote?.body
}