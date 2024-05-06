package com.fjr619.composefirebasedb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppNavController(
    navHostController: NavHostController = rememberNavController()
): AppNavController = remember(navHostController){
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
        if (lifecycleIsResumed()) {
            doSomething()
            navController.navigate(AppRoute.TaskScreen)
        }
    }

    fun navigateUp() {
        if (lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }
}