package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView

class NotesPagerPresenterImpl(
    private val model: NoteModel,
    private var view: NotesPagerActivityView?
) : NotesPagerPresenter {

    override fun init(id: Long, list: List<Note>) {
        var i = 0L
        while (id != list[i.toInt()].id) {
            i++
        }
        view?.init(model.getAll(), i)
    }
}
