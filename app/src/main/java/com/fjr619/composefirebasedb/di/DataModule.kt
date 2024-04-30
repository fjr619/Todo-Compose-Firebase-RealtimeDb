package com.fjr619.composefirebasedb.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseDatabase> {
        Firebase.database("https://compose-firebase-d1be4-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }
    single<DatabaseReference>{ (get() as FirebaseDatabase).getReference("comppose-firebase-data") }
}