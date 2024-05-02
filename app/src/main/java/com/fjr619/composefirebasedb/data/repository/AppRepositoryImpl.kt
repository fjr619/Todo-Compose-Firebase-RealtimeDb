package com.fjr619.composefirebasedb.data.repository

import com.fjr619.composefirebasedb.data.model.toDomain
import com.fjr619.composefirebasedb.data.model.toEntity
import com.fjr619.composefirebasedb.data.realtime_database.RealtimeDatabaseSource
import com.fjr619.composefirebasedb.domain.model.RequestState
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImpl(
    private val realtimeDatabaseSource: RealtimeDatabaseSource
) : AppRepository {
    override fun readActiveTasks(): Flow<RequestState<List<Task>>> {
        return realtimeDatabaseSource.readActiveTasks()
            .map {
                it.map { entity ->
                    entity.toDomain()
                }
            }.map {
                if (it.isNotEmpty()) {
                    RequestState.Success(it)
                } else {
                    RequestState.Error("empty data")
                }
            }
    }

    override fun readCompletedTasks(): Flow<RequestState<List<Task>>> {
        return realtimeDatabaseSource.readCompletedTasks()
            .map {
                it.map { entity ->
                    entity.toDomain()
                }
            }.map {
                if (it.isNotEmpty()) {
                    RequestState.Success(it)
                } else {
                    RequestState.Error("empty data")
                }
            }
    }

    override suspend fun addTask(task: Task) {
        realtimeDatabaseSource.addTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        realtimeDatabaseSource.updateTask(task.toEntity())
    }

    override suspend fun setCompleted(task: Task, taskCompleted: Boolean) {
        realtimeDatabaseSource.setCompleted(task.toEntity(), taskCompleted)
    }

    override suspend fun setFavorite(task: Task, isFavorite: Boolean) {
        realtimeDatabaseSource.setFavorite(task.toEntity(), isFavorite)
    }

    override suspend fun deleteTask(task: Task) {
        realtimeDatabaseSource.deleteTask(task.toEntity())
    }
}