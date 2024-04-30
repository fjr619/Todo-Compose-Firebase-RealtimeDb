package com.fjr619.composefirebasedb.domain.repository

import com.fjr619.composefirebasedb.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getItems (): Flow<Result<List<Task>>>
}