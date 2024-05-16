package com.fjr619.composefirebasedb.di

import com.fjr619.composefirebasedb.ui.screens.SharedViewModel
import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import com.fjr619.composefirebasedb.ui.screens.task.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(get()) }
    viewModel<SharedViewModel> { SharedViewModel() }
    viewModel<TaskViewModel> { TaskViewModel(get(), get()) }
}

