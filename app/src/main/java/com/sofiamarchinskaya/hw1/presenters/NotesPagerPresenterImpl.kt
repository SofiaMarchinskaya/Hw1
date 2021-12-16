package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.models.Note
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView

class NotesPagerPresenterImpl(
    private var view:NotesPagerActivityView?
) : NotesPagerPresenter{
    private var list:List<Note> = emptyList()
    override fun init() {
        view?.init(list)
    }


}