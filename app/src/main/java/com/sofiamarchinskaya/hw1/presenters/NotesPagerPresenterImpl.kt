package com.sofiamarchinskaya.hw1.presenters

import android.os.Bundle
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView
import kotlinx.coroutines.runBlocking

class NotesPagerPresenterImpl(
    private var view: NotesPagerActivityView?,
    private val database: AppDatabase
) : NotesPagerPresenter {

    override fun init(extras: Bundle?): Unit = runBlocking {
        val list = database.noteDao().getAll()
        var index = 0L
        val id = extras?.getLong(Constants.ID)
        while (index != id) {
            index++
        }
        view?.init(list, index - 1)
    }
}