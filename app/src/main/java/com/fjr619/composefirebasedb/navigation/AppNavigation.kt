package com.fjr619.composefirebasedb.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.fjr619.composefirebasedb.ui.screens.home.HomeScreen
import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import com.fjr619.composefirebasedb.ui.screens.task.TaskScreen
import com.fjr619.composefirebasedb.ui.screens.task.TaskViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val appNavController = rememberAppNavController()

    NavHost(
        navController = appNavController.navController,
        startDestination = AppRoute.RootNav
    ) {

        navigation<AppRoute.RootNav>(
            startDestination = AppRoute.HomeScreen
        ) {
            composable<AppRoute.HomeScreen> { entry ->
                val homeViewModel = koinViewModel<HomeViewModel>()
                val state by homeViewModel.state.collectAsStateWithLifecycle()

                Button(onClick = dropUnlessResumed {
                    // do something
                }) {
                    Text(text = "Click me")
                }

                HomeScreen(
                    state = state,
                    navigateToTask = {
                        appNavController.navigateToTask(it)
                    },
                    onEvent = homeViewModel::onEvent
                )
            }

            composable<AppRoute.TaskScreen>(
                typeMap = NavTypeMap.taskTypeMap
            ) { entry ->
                val taskViewModel: TaskViewModel = koinViewModel()
                val state by taskViewModel.state.collectAsStateWithLifecycle()

                TaskScreen(
                    state = state,
                    onNavigateUp = {
                        appNavController.navigateUp()
                    },
                    onTaskEvent = taskViewModel::onEvent
                )
            }
        }
    }
}

