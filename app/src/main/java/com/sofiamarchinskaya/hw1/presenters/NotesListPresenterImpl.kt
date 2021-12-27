package com.sofiamarchinskaya.hw1.presenters

import android.view.ContextMenu
import com.sofiamarchinskaya.hw1.view.framework.NotesListView
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.framework.NotesListPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotesListPresenterImpl(
    private var view: NotesListView?,
    coroutineScope: CoroutineScope
) : NotesListPresenter, CoroutineScope by coroutineScope {
    private var clickedNote: Note? = null

    override fun init() {
        launch {
            view?.initAdapter(AppDatabase.getDataBase().noteDao().getAll())
        }
    }

    override fun onItemClick(note: Note) {
        view?.openAboutItemActivity(note)
    }

    override fun createMenu(menu: ContextMenu?) {
        view?.onMenuCreated(menu)
    }

    override fun onResume() {
        launch {
            view?.update(AppDatabase.getDataBase().noteDao().getAll())
        }
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