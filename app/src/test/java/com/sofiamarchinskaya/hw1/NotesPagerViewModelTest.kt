package com.sofiamarchinskaya.hw1

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.dao.NoteDao
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class NotesPagerViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: NotesPagerViewModel
    private lateinit var listObserver: Observer<List<Note>>
    private lateinit var indexObserver: Observer<Long>
    private lateinit var repository: NoteRepository

    private val testId = 3L


    @Before
    fun setup() {
        repository = NoteRepositoryImpl()
        viewModel = NotesPagerViewModel()
        listObserver = mock()
        indexObserver = mock()
        viewModel.index.observeForever(indexObserver)
        viewModel.list.observeForever(listObserver)
        JUnitCore().run(App::class.java)
    }

    @Test
    fun when_init_called() = runBlocking {
        viewModel.init(testId)
        verify(listObserver).onChanged(viewModel.list.value)
        verify(indexObserver).onChanged(viewModel.index.value)
    }
}