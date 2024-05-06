package com.fjr619.composefirebasedb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.RootNav
    ) {

        navigation<AppRoute.RootNav>(
            startDestination = AppRoute.HomeScreen
        ) {
            composable<AppRoute.HomeScreen> { entry ->
                val homeViewModel = koinViewModel<HomeViewModel>()
                val sharedViewModel =
                    entry.sharedViewModel(navController = navController)
                val state by homeViewModel.state.collectAsStateWithLifecycle()

                HomeScreen(
                    state = state,
                    navigateToTask = {
                        if (navController.lifecycleIsResumed()) {
                            sharedViewModel.set(it)
                            navController.navigate(AppRoute.TaskScreen)
                        }
                    },
                    onEvent = homeViewModel::onEvent
                )
            }

            composable<AppRoute.TaskScreen>(
//                                typeMap = mapOf(typeOf<Task>() to TaskParametersType)
            ) { entry ->
                val sharedViewModel = entry.sharedViewModel(navController)
                val currentTask by sharedViewModel.currentTask.collectAsStateWithLifecycle()
                val taskViewModel = koinViewModel<TaskViewModel>(parameters = { parametersOf(currentTask) })
                val state by taskViewModel.state.collectAsStateWithLifecycle()

//                                val taskParameter = entry.toRoute<AppNavigation.TaskScreen>().task
//                                println("taskParameter $taskParameter")

                TaskScreen(
                    state = state,
                    onNavigateUp = {
                        if (navController.lifecycleIsResumed()) {
                            navController.navigateUp()
                        }
                    },
                    onTaskEvent = taskViewModel::onEvent
                )
            }
        }
    }
}

fun NavController.lifecycleIsResumed() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
