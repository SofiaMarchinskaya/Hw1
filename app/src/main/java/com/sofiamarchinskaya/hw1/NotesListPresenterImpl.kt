package com.sofiamarchinskaya.hw1

import com.sofiamarchinskaya.hw1.presenters.NotesListPresenter

class NotesListPresenterImpl(
    private var view: NotesList?,
    private val model: NotesModel,
) : NotesListPresenter {

    override fun init() {
        view?.initAdapter(model.list)
    }

    override fun getDataToExtra(pos: Int): String =
        model.list[pos].title + "\n" + model.list[pos].text

}