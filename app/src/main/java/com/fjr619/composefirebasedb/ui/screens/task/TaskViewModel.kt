package com.fjr619.composefirebasedb.ui.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TaskViewModel(
    private val selectedTask: Task,
    private val repository: AppRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskUiState())
    val state = _state.asStateFlow()

    companion object {
        const val DEFAULT_TITLE = "Enter the Title"
        const val DEFAULT_DESCRIPTION = "Add some description"
    }

    init {
        println("INI TASK VM get data for $selectedTask")
        _state.update {
            it.copy(
                currentTask = selectedTask
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