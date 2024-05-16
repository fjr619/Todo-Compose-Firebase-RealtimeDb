package com.fjr619.composefirebasedb.ui.screens.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import com.fjr619.composefirebasedb.navigation.AppRoute
import com.fjr619.composefirebasedb.navigation.NavTypeMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel() {

    companion object {
        const val DEFAULT_TITLE = "Enter the Title"
        const val DEFAULT_DESCRIPTION = "Add some description"
    }

    private val _state = MutableStateFlow(TaskUiState())
    val state = _state.asStateFlow()

    init {
        println("INI TASK VM get data")
        _state.update {
            it.copy(
                currentTask = savedStateHandle.toRoute<AppRoute.TaskScreen>(
                    typeMap = NavTypeMap.taskTypeMap
                ).task
            )
        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.SetTitle -> {
                _state.update {
                    it.copy(
                        currentTask = it.currentTask.copy(
                            title = event.title
                        )
                    )
                }
            }

            is TaskEvent.SetDesc -> {
                _state.update {
                    it.copy(
                        currentTask = it.currentTask.copy(
                            desc = event.desc
                        )
                    )
                }
            }

            is TaskEvent.Add -> {
                state.onEach {
                    repository.addTask(it.currentTask)
                }.launchIn(viewModelScope)
            }

            is TaskEvent.Update -> {
                state.onEach {
                    repository.updateTask(it.currentTask)
                }.launchIn(viewModelScope)
            }
        }
    }
}