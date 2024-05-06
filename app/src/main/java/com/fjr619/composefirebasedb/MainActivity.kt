package com.fjr619.composefirebasedb

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.ui.screens.home.HomeScreen
import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import com.fjr619.composefirebasedb.ui.screens.sharedViewModel
import com.fjr619.composefirebasedb.ui.screens.task.TaskScreen
import com.fjr619.composefirebasedb.ui.screens.task.TaskViewModel
import com.fjr619.composefirebasedb.ui.theme.ComposeFirebaseDBTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //fix fab above keyboard
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }

        setContent {
            ChangeSystemBarsTheme(!isSystemInDarkTheme())
            KoinAndroidContext {
                ComposeFirebaseDBTheme {
                    // A surface container using the 'background' color from the theme
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppNavigation.RootNav
                    ) {

                        navigation<AppNavigation.RootNav>(
                            startDestination = AppNavigation.HomeScreen
                        ) {
                            composable<AppNavigation.HomeScreen> { entry ->
                                val homeViewModel = koinViewModel<HomeViewModel>()
                                val sharedViewModel =
                                    entry.sharedViewModel(navController = navController)
                                val state by homeViewModel.state.collectAsStateWithLifecycle()

                                HomeScreen(
                                    state = state,
                                    navigateToTask = {
                                        sharedViewModel.set(it)
                                        navController.navigate(AppNavigation.TaskScreen)
                                    },
                                    onEvent = homeViewModel::onEvent
                                )
                            }

                            composable<AppNavigation.TaskScreen>(
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
                                        navController.navigateUp()
                                    },
                                    onTaskEvent = taskViewModel::onEvent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComponentActivity.ChangeSystemBarsTheme(lightTheme: Boolean) {
    val barColor = Color.Transparent.toArgb()
    LaunchedEffect(lightTheme) {
        if (lightTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    barColor, barColor,
                ),
                navigationBarStyle = SystemBarStyle.light(
                    barColor, barColor,
                ),
            )
        } else {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    barColor,
                ),
                navigationBarStyle = SystemBarStyle.dark(
                    barColor,
                ),
            )
        }
    }
}

sealed class AppNavigation {
    @Serializable
    data object RootNav: AppNavigation()

    @Serializable
    data object HomeScreen: AppNavigation()

    @Serializable
    data object TaskScreen: AppNavigation()
}

//Still buggy, waiting for next compose navigation version
//val TaskParametersType = object : NavType<Task>(isNullableAllowed = false){
//    override fun get(bundle: Bundle, key: String): Task?         {
//        return bundle.getParcelable(key)
//    }
//
//    override fun parseValue(value: String): Task {
//        return Json.decodeFromString<Task>(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: Task) {
//        bundle.putParcelable(key, value)
//    }
//
//}

