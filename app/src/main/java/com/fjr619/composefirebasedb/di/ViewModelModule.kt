package com.fjr619.composefirebasedb.di

import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(get()) }
}