package com.fjr619.composefirebasedb.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.fjr619.composefirebasedb.ui.screens.home.HomeScreen
import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import com.fjr619.composefirebasedb.ui.screens.sharedViewModel
import com.fjr619.composefirebasedb.ui.screens.task.TaskScreen
import com.fjr619.composefirebasedb.ui.screens.task.TaskViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
                val sharedViewModel =
                    entry.sharedViewModel(navController = appNavController.navController)
                val state by homeViewModel.state.collectAsStateWithLifecycle()

                Button(onClick = dropUnlessResumed{
                    // do something
                }) {
                    Text(text = "Click me")
                }

                HomeScreen(
                    state = state,
                    navigateToTask = {
                        appNavController.navigateToTask {
                            sharedViewModel.set(it)
                        }
                    },
                    onEvent = homeViewModel::onEvent
                )
            }

            composable<AppRoute.TaskScreen>(
//                                typeMap = mapOf(typeOf<Task>() to TaskParametersType)
            ) { entry ->
                val sharedViewModel = entry.sharedViewModel(appNavController.navController)
                val currentTask by sharedViewModel.currentTask.collectAsStateWithLifecycle()
                val taskViewModel = koinViewModel<TaskViewModel>(parameters = { parametersOf(currentTask) })
                val state by taskViewModel.state.collectAsStateWithLifecycle()

//                                val taskParameter = entry.toRoute<AppNavigation.TaskScreen>().task
//                                println("taskParameter $taskParameter")

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