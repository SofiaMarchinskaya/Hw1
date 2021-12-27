package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteModel
import com.sofiamarchinskaya.hw1.presenters.framework.NoteInfoPresenter
import com.sofiamarchinskaya.hw1.view.framework.NoteInfoView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteInfoPresenterImpl(
    private val model: NoteModel,
    private var view: NoteInfoView?,
    coroutineScope: CoroutineScope
) : NoteInfoPresenter, CoroutineScope by coroutineScope {

    override fun onSaveNote(title: String, text: String, id: Long) {
        launch {
            if (id == Constants.INVALID_ID) {
                model.insert(Note(title = title, body = text))
            } else {
                model.update(Note(id, title, text))
            }
            view?.onSuccessfullySaved()
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