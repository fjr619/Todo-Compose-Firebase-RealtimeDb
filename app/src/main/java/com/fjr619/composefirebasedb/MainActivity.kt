package com.fjr619.composefirebasedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fjr619.composefirebasedb.ui.screens.home.HomeViewModel
import com.fjr619.composefirebasedb.ui.theme.ComposeFirebaseDBTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
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
                    NavHost(navController = navController, startDestination = "home") {
                        composable(
                            route = "home"
                        ) {
                            val homeViewModel = koinViewModel<HomeViewModel>()
                            val state by homeViewModel.state.collectAsStateWithLifecycle()
                            Surface(modifier = Modifier.fillMaxSize()) {
                                Text(text = "$state")
                                Column {
                                    Button(onClick = {
                                        homeViewModel.add()
                                    }) {
                                        Text(text = "ADD")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}