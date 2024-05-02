package com.fjr619.composefirebasedb.domain.repository

import com.fjr619.composefirebasedb.domain.model.RequestState
import com.fjr619.composefirebasedb.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getItems (): Flow<Result<List<Task>>>

    fun readActiveTasks(): Flow<RequestState<List<Task>>>
    fun readCompletedTasks(): Flow<RequestState<List<Task>>>
    suspend fun addTask(task: Task)
}