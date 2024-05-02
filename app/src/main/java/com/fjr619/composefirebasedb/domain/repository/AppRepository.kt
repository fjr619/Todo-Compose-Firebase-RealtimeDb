package com.fjr619.composefirebasedb.domain.repository

import com.fjr619.composefirebasedb.domain.model.RequestState
import com.fjr619.composefirebasedb.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun connection(): Flow<Boolean>
    fun readActiveTasks(): Flow<RequestState<List<Task>>>
    fun readCompletedTasks(): Flow<RequestState<List<Task>>>
    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)
    suspend fun setCompleted(task: Task, taskCompleted: Boolean)
    suspend fun setFavorite(task: Task, isFavorite: Boolean)
    suspend fun deleteTask(task: Task)
}