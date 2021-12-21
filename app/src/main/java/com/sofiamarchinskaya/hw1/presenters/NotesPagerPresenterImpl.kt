package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView
import kotlinx.coroutines.runBlocking

class NotesPagerPresenterImpl(
    private var view: NotesPagerActivityView?
) : NotesPagerPresenter {

    override fun init(id: Long?): Unit = runBlocking {
        val list = AppDatabase.getDataBase().noteDao().getAll()
        var index = 0L
        while (index != id) {
            index++
        }
        view?.init(list, index - 1)
    }
}