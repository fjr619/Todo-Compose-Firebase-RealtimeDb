package com.fjr619.composefirebasedb.data.realtime_database

import com.fjr619.composefirebasedb.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseSource {
    fun readActiveTasks(): Flow<List<TaskEntity>>
    fun readCompletedTasks(): Flow<List<TaskEntity>>
    suspend fun addTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun setCompleted(task: TaskEntity, completed: Boolean)
    suspend fun setFavorite(task: TaskEntity, favorite: Boolean)
    suspend fun deleteTasl(task: TaskEntity)



}