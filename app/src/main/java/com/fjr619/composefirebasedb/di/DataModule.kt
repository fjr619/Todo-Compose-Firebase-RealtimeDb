package com.fjr619.composefirebasedb.di

import com.fjr619.composefirebasedb.data.realtime_database.RealtimeDatabaseSource
import com.fjr619.composefirebasedb.data.realtime_database.RealtimeDatabaseSourceImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseDatabase> {
        Firebase.database("https://compose-firebase-d1be4-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    single<DatabaseReference>(named("taskReferences")) {
        (get() as FirebaseDatabase).getReference("taskDatas")
    }

    single<DatabaseReference>(named("connectionReferences")) {
        (get() as FirebaseDatabase).getReference(".info/connected")
    }

    single<RealtimeDatabaseSource> {
        RealtimeDatabaseSourceImpl(
            get(named("taskReferences")),
            get(named("connectionReferences"))
        )
    }
}