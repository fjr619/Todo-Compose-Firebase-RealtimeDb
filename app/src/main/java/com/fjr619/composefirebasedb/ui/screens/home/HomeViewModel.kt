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
import kotlinx.coroutines.flow.combine
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

    private val databaseReference: DatabaseReference by inject<DatabaseReference>()

    init {
        onEvent(HomeEvent.GetData)
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.GetData -> {
                getTasks()
            }
            is HomeEvent.Delete -> {}
            is HomeEvent.SetFavorite -> {}
            is HomeEvent.SetCompleted -> {}
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
            val readActive = async { repository.readActiveTasks() }
            val readCompleted = async { repository.readCompletedTasks() }

            combine(readActive.await(), readCompleted.await()) { active, completed ->
                Pair(active, completed)
            }.collect { result ->
                _state.update {
                    it.copy(
                        activeTask = result.first,
                        completedTask = result.second
                    )
                }
            }
        }
    }

    fun add() {
        val key = databaseReference.push().key
        key?.let {nonNullKey ->
            databaseReference.child(nonNullKey).setValue(TaskEntity().copy(id = nonNullKey))
        }
    }
}