package com.fjr619.composefirebasedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.fjr619.composefirebasedb.ui.screens.home.HomeScreen
import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import com.fjr619.composefirebasedb.ui.theme.ComposeFirebaseDBTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinAndroidContext {
                ComposeFirebaseDBTheme {
                    // A surface container using the 'background' color from the theme

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "root_navigation"
                    ) {

                        navigation(
                            route = "root_navigation",
                            startDestination = "home"
                        ) {
                            composable(
                                route = "home"
                            ) {
                                val homeViewModel = koinViewModel<HomeViewModel>()
                                val state by homeViewModel.state.collectAsStateWithLifecycle()

                                HomeScreen(
                                    state = state,
                                    navigateToTask = {},
                                    onEvent = homeViewModel::onEvent
                                )
                            }

                            composable(
                                route = "task"
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}