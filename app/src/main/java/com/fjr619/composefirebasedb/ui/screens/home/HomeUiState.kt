package com.fjr619.composefirebasedb.ui.screens.home

import com.fjr619.composefirebasedb.domain.model.RequestState
import com.fjr619.composefirebasedb.domain.model.Task


data class HomeUiState(
    val connection: Boolean = true,
    val activeTask: RequestState<List<Task>> = RequestState.Idle,
    val completedTask: RequestState<List<Task>> = RequestState.Idle,
)