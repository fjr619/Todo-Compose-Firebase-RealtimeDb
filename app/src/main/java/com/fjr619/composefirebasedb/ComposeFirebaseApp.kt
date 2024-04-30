package com.fjr619.composefirebasedb

import android.app.Application
import com.fjr619.composefirebasedb.di.dataModule
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ComposeFirebaseApp: Application() {

    private val firebaseDatabase by inject<FirebaseDatabase>()
    private val databaseReference by inject<DatabaseReference>()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@ComposeFirebaseApp)
            modules(
                dataModule
            )
        }

        firebaseDatabase.setPersistenceEnabled(true)
        databaseReference.keepSynced(true)
    }
}