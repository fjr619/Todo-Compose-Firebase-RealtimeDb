package com.fjr619.composefirebasedb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fjr619.composefirebasedb.domain.model.Task

@Composable
fun rememberAppNavController(
    navHostController: NavHostController = rememberNavController()
): AppNavController = remember(navHostController) {
    AppNavController(navHostController)
}

@Stable
class AppNavController(
    val navController: NavHostController
) {

    /**
     * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
     *
     * This is used to de-duplicate navigation events.
     */
    private fun lifecycleIsResumed() =
        navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

    fun navigateToTask(doSomething: () -> Unit) {
        doSomething()
        navController.navigate(AppRoute.TaskScreen)
    }

    fun navigateToTask(task: Task) {
        navController.navigate(AppRoute.TaskScreen(task))
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}