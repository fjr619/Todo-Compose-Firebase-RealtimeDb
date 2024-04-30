package com.fjr619.composefirebasedb.data.realtime_database

import com.fjr619.composefirebasedb.data.model.TaskEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimeDatabaseSourceImpl(
    private val databaseReference: DatabaseReference
): RealtimeDatabaseSource {
    override fun getItems(): Flow<List<TaskEntity>> = callbackFlow {
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val responses = snapshot.children
                val results = mutableListOf<TaskEntity>()

                for (item in responses) {
                    val data = item.getValue(TaskEntity::class.java)
                    data?.let {
                        results.add(it)
                    }
                }

                trySend(results)

            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        databaseReference.addValueEventListener(valueEvent)
        awaitClose {
            databaseReference.removeEventListener(valueEvent)
            close()
        }
    }
}