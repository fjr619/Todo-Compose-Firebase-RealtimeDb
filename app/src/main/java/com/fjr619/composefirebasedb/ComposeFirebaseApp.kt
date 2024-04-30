package com.fjr619.composefirebasedb

import android.app.Application
import com.fjr619.composefirebasedb.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ComposeFirebaseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@ComposeFirebaseApp)
            dataModule
        }
    }
}