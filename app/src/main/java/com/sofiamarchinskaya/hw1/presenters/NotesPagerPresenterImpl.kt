package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView

class NotesPagerPresenterImpl(
    private val model: NoteModel,
    private var view: NotesPagerActivityView?
) : NotesPagerPresenter {

    override fun listCollected(list: List<Note>, id: Long): Int {
        var i = 0L
        while (id != list[i.toInt()].id) {
            i++
        }
        return i.toInt()
    }

    override fun init() {
        view?.init(model.getAll())
    }

    override fun onNoteSaved(id: Long) {
        view?.onNoteSaved(model.getAll(), id)
    }
}