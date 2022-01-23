package com.sofiamarchinskaya.hw1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sofiamarchinskaya.hw1.models.ListWithIndex
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class NotesPagerViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: NotesPagerViewModel
    private lateinit var listIndexObserver: Observer<ListWithIndex>
    private lateinit var repository: NoteRepositoryImpl
    private lateinit var list: List<Note>
    private val testId = 1L

    @Before
    fun setup() {
        list = listOf(Note(testId, "title1", "text1"))
        repository = mock()
        `when`(repository.getAll()) doReturn (flow {
            delay(10)
            emit(list)
        })
        viewModel = NotesPagerViewModel(repository)
        listIndexObserver = mock()
        viewModel.listWithIndex.observeForever(listIndexObserver)
    }
    
    @Test
    fun when_init_called() = runBlocking {
        viewModel.init(testId)
        viewModel.listWithIndex.observeForever { listWithIndex ->
            Assert.assertEquals(list.size, listWithIndex.list.size)
            list.forEachIndexed { index, note ->
                Assert.assertEquals(note, listWithIndex.list[index])
            }
            Assert.assertEquals(list.indexOfFirst { it.id == testId }.toLong(), listWithIndex.index)
        }
    }
}