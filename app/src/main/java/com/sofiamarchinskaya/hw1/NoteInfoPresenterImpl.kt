package com.sofiamarchinskaya.hw1

import com.sofiamarchinskaya.hw1.presenters.NoteInfoPresenter

class NoteInfoPresenterImpl(
    private var view: NoteInfo?,
    private val model: SaveModel
) : NoteInfoPresenter {

    override fun onSave(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            view?.onFailed()
            return
        }
        model.onSave(title, text)
        view?.onSaveComplete()
    }

    override fun onDestroy() {
        view = null
    }

}