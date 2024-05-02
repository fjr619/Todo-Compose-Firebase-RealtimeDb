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
    override fun readActiveTasks(): Flow<List<TaskEntity>> = callbackFlow {
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

        databaseReference.orderByChild("completed").equalTo(false).addValueEventListener(valueEvent)
        awaitClose {
            databaseReference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun readCompletedTasks(): Flow<List<TaskEntity>> = callbackFlow {
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

        databaseReference.orderByChild("completed").equalTo(true).addValueEventListener(valueEvent)
        awaitClose {
            databaseReference.removeEventListener(valueEvent)
            close()
        }
    }

    override suspend fun addTask(task: TaskEntity) {
        val key = databaseReference.push().key
        key?.let {nonNullKey ->
            databaseReference.child(nonNullKey).setValue(task.copy(id = nonNullKey))
        }
    }

    override suspend fun updateTask(task: TaskEntity) {
        databaseReference.child(task.id).setValue(task)
    }

    override suspend fun setCompleted(task: TaskEntity, completed: Boolean) {
        val map = HashMap<String,Any>()
        map["completed"] = completed
        databaseReference.child(task.id).updateChildren(map)
    }

    override suspend fun setFavorite(task: TaskEntity, favorite: Boolean) {
        val map = HashMap<String,Any>()
        map["favorite"] = favorite
        databaseReference.child(task.id).updateChildren(map)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        databaseReference.child(task.id).removeValue()
    }
}