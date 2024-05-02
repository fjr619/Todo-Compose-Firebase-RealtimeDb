package com.fjr619.composefirebasedb.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.ui.screens.home.components.DisplayTasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeUiState,
    navigateToTask: (Task) -> Unit,
    onEvent: (HomeEvent) -> Unit
) {

    Scaffold(
        topBar = {
            Column {
                AnimatedVisibility(visible = !state.connection) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .statusBarsPadding()
                            .padding(8.dp),
                        text = "Firebase disconnected",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                CenterAlignedTopAppBar(
                    windowInsets = if (state.connection) TopAppBarDefaults.windowInsets else WindowInsets(
                        top = 0.dp,
                        bottom = 0.dp
                    ),
                    title = { Text(text = "Home") },
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToTask(Task()) },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DisplayTasks(
                modifier = Modifier.weight(1f),
                tasks = state.activeTask,
                onSelect = {
                    navigateToTask(it)
                },
                onFavorite = { task, favorite ->
                    onEvent(
                        HomeEvent.SetFavorite(
                            task,
                            favorite
                        )
                    )
                },
                onComplete = { task, complete ->
                    onEvent(
                        HomeEvent.SetCompleted(
                            task,
                            complete
                        )
                    )
                },

                )

            Spacer(modifier = Modifier.height(24.dp))

            DisplayTasks(
                modifier = Modifier.weight(1f),
                tasks = state.completedTask,
                showActive = false,
                onComplete = { task, complete ->
                    onEvent(
                        HomeEvent.SetCompleted(
                            task,
                            complete
                        )
                    )
                },
                onDelete = { task -> onEvent(HomeEvent.Delete(task)) }
            )
        }

    }
}