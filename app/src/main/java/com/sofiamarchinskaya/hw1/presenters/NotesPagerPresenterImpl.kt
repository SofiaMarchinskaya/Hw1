package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.models.framework.NoteModel
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView
import kotlinx.coroutines.CoroutineScope

class NotesPagerPresenterImpl(
    private val model: NoteModel,
    private var view: NotesPagerActivityView?,
    coroutineScope: CoroutineScope
) : NotesPagerPresenter, CoroutineScope by coroutineScope {

    override fun init(id: Long?) {
        var index = 0L
        while (index != id) {
            index++
        }
        view?.init(model.getAll(), index - 1)
    }
}