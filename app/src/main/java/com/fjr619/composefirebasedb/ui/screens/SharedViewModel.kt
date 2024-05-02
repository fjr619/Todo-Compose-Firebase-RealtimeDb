package com.fjr619.composefirebasedb.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.fjr619.composefirebasedb.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.androidx.compose.koinViewModel

//menggunakan shared view model navigation scope supaya bisa share data class
class SharedViewModel: ViewModel() {

    private val _currentTask: MutableStateFlow<Task> = MutableStateFlow(Task())
    val currentTask = _currentTask.asStateFlow()

    init {
        println("INI SHARED VIEW MODEL")
    }

    fun set(task: Task) {
        _currentTask.update {
            task
        }
    }
}


@Composable
fun NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): SharedViewModel {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(viewModelStoreOwner = parentEntry)
}