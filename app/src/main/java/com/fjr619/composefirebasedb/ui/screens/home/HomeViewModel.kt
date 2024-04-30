package com.fjr619.composefirebasedb.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.composefirebasedb.data.model.TaskEntity
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.domain.repository.AppRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class HomeState(
    val loading: Boolean = false,
    val items: List<Task> = emptyList()
)

class HomeViewModel(
    private val repository: AppRepository
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val databaseReference: DatabaseReference by inject<DatabaseReference>()

    init {
        println("init vm")
        getItems()
    }

    fun getItems() {
//        viewModelScope.launch {
//            repository.getItems()
//                .catch {
//                    _state.update { state ->
//                        state.copy(loading = false)
//                    }
//                }
//                .collect {
//                    _state.update { state ->
//                        state.copy(
//                            items = it.getOrDefault(emptyList()),
//                            loading = false
//                        )
//                    }
//                }
//        }

        _state.update {
            it.copy(loading = true)
        }

        repository.getItems()
            .catch {
                _state.update { state ->
                    state.copy(loading = false)
                }
            }
            .onEach {
                println("adwdaw $it")
                _state.update { state ->
                    state.copy(
                        items = it.getOrDefault(emptyList()),
                        loading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun add() {
        val key = databaseReference.push().key
        key?.let {nonNullKey ->
            databaseReference.child(nonNullKey).setValue(TaskEntity().copy(id = nonNullKey))
        }
    }
}