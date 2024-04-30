package com.fjr619.composefirebasedb.di

import com.fjr619.composefirebasedb.data.repository.AppRepositoryImpl
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import org.koin.dsl.module

val domainModule = module {
    factory<AppRepository> { AppRepositoryImpl(get()) }
}