package com.fjr619.composefirebasedb.navigation

import com.fjr619.composefirebasedb.domain.model.Task
import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    data object RootNav : AppRoute()

    @Serializable
    data object HomeScreen : AppRoute()

    @Serializable
    data class TaskScreen(val task: Task) : AppRoute()
}