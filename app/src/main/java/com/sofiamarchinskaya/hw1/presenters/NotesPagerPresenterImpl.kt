package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.models.framework.NoteModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView

class NotesPagerPresenterImpl(
    private val model: NoteModel,
    private var view: NotesPagerActivityView?
) : NotesPagerPresenter {

    override fun init(id: Long) {
        view?.init(model.getAll(), id - 1)
    }
}