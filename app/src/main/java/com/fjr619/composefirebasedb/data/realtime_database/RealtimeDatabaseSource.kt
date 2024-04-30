package com.fjr619.composefirebasedb.data.realtime_database

import com.fjr619.composefirebasedb.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseSource {
    fun getItems(): Flow<List<TaskEntity>>

}