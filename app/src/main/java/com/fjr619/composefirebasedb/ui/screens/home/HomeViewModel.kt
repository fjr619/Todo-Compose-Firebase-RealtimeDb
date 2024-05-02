package com.fjr619.composefirebasedb.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.composefirebasedb.data.model.TaskEntity
import com.fjr619.composefirebasedb.domain.model.RequestState
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.Future

class HomeViewModel(
    private val repository: AppRepository
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        onEvent(HomeEvent.GetData)
        viewModelScope.launch {
            repository.connection().collect {
                println("connection $it")
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.GetData -> {
                getTasks()
            }
            is HomeEvent.Delete -> {
                setDelete(event.task)
            }
            is HomeEvent.SetFavorite -> {
                setFavorite(event.task, event.isFavorite)
            }
            is HomeEvent.SetCompleted -> {
                setCompleted(event.task, event.completed)
            }
        }
    }

    private fun setDelete(task: Task) {
        viewModelScope.launch{
            repository.deleteTask(task)
        }
    }

    private fun setFavorite(task: Task, favorite: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(task, favorite)
        }
    }

    private fun setCompleted(task: Task, completed: Boolean) {
        viewModelScope.launch {
            repository.setCompleted(task, completed)
        }
    }

    private fun getTasks() {
        _state.update {
            it.copy(
                activeTask = RequestState.Loading,
                completedTask = RequestState.Loading
            )
        }

        viewModelScope.launch {
            repository.readActiveTasks().collectLatest { result ->
                _state.update {
                    it.copy(
                        activeTask = result
                    )
                }
            }
        }

        viewModelScope.launch {
            repository.readCompletedTasks().collectLatest { result ->
                _state.update {
                    it.copy(
                        completedTask = result
                    )
                }
            }
        }
    }
}