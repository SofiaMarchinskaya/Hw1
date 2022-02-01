package com.sofiamarchinskaya.hw1.koin

import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import com.sofiamarchinskaya.hw1.utils.ItemsFilter
import com.sofiamarchinskaya.hw1.utils.QueryFilter
import com.sofiamarchinskaya.hw1.viewmodels.MainActivityViewModel
import com.sofiamarchinskaya.hw1.viewmodels.NoteInfoViewModel
import com.sofiamarchinskaya.hw1.viewmodels.NotesListViewModel
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { NoteInfoViewModel(get()) }
    viewModel { NotesListViewModel(get(),get()) }
    viewModel { NotesPagerViewModel(get()) }

    single<NoteRepository> { NoteRepositoryImpl() }
    single<QueryFilter<Note>>{ ItemsFilter() }
}