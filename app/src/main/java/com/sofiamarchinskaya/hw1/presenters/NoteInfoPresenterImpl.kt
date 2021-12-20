package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.framework.NoteInfoPresenter
import com.sofiamarchinskaya.hw1.view.framework.NoteInfoView
import kotlinx.coroutines.runBlocking

class NoteInfoPresenterImpl(
    private var view: NoteInfoView?,
    private val database: AppDatabase
) : NoteInfoPresenter {

    override fun onSaveNote(title: String, text: String, id: Long): Unit = runBlocking {
        if (id == Constants.INVALID_ID) {
            database.noteDao().insert(Note(title = title, body = text))
        } else {
            database.noteDao().update(Note(id, title, text))
        }
    }

    override fun checkNote(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            view?.onSaveDisabled()
        } else
            view?.onSaveAllowed()
    }

    override fun onDestroy() {
        view = null
    }
}