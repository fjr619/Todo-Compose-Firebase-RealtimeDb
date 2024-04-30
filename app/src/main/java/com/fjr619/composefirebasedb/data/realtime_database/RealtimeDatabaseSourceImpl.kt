package com.fjr619.composefirebasedb.data.realtime_database

import com.fjr619.composefirebasedb.data.model.WeightEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimeDatabaseSourceImpl(
    private val databaseReference: DatabaseReference
): RealtimeDatabaseSource {
    override fun getItems(): Flow<List<WeightEntity>> = callbackFlow {
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.getValue<List<WeightEntity>>()
                trySend(items ?: throw Exception("null data"))

            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        }

        databaseReference.addValueEventListener(valueEvent)
        awaitClose {
            databaseReference.addValueEventListener(valueEvent)
            close()
        }
    }
}