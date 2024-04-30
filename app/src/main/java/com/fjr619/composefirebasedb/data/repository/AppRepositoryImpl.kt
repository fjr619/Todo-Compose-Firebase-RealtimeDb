package com.fjr619.composefirebasedb.data.repository

import com.fjr619.composefirebasedb.data.model.toDomain
import com.fjr619.composefirebasedb.data.realtime_database.RealtimeDatabaseSource
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImpl(
    private val realtimeDatabaseSource: RealtimeDatabaseSource
): AppRepository {
    override fun getItems(): Flow<Result<List<Task>>> {
        return realtimeDatabaseSource.getItems()
            .map {
                it.map {entity ->
                    entity.toDomain()
                }
            }.map {
                if (it.isNotEmpty()) {
                    Result.success(it)
                } else {
                    Result.failure(Exception("empty data"))
                }

            }
    }
}