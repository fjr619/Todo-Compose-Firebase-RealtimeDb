package com.fjr619.composefirebasedb.ui.screens.home

import com.fjr619.composefirebasedb.domain.model.Task

sealed class HomeEvent {
    data object GetData : HomeEvent()

    //    data class Add(val task: TodoTask) : HomeEvent()
//    data class Update(val task: TodoTask) : HomeEvent()
    data class Delete(val task: Task) : HomeEvent()
    data class SetCompleted(val task: Task, val completed: Boolean) : HomeEvent()
    data class SetFavorite(val task: Task, val isFavorite: Boolean) : HomeEvent()
}