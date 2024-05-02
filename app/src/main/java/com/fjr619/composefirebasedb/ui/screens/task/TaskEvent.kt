package com.fjr619.composefirebasedb.ui.screens.task

sealed class TaskEvent {
    data object Add : TaskEvent()
    data object Update : TaskEvent()
    data class SetTitle(val title: String) : TaskEvent()
    data class SetDesc(val desc: String) : TaskEvent()
}