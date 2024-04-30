package com.fjr619.composefirebasedb.data.realtime_database

import com.fjr619.composefirebasedb.data.model.WeightEntity
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabaseSource {
    fun getItems(): Flow<List<WeightEntity>>

}