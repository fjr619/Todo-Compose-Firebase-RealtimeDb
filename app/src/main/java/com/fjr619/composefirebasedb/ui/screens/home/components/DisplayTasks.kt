package com.fjr619.composefirebasedb.ui.screens.home.components

import android.text.style.TtsSpan.TimeBuilder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fjr619.composefirebasedb.domain.model.RequestState
import com.fjr619.composefirebasedb.domain.model.Task
import com.fjr619.composefirebasedb.ui.screens.components.ErrorScreen
import com.fjr619.composefirebasedb.ui.screens.components.LoadingScreen

@Composable
fun DisplayTasks(
    modifier: Modifier,
    tasks: RequestState<List<Task>>,
    showActive: Boolean = true,
    onSelect: ((Task) -> Unit)? = null,
    onFavorite: ((Task, Boolean) -> Unit)? = null,
    onComplete: ((Task, Boolean) -> Unit)? = null,
    onDelete: ((Task) -> Unit)? = null,
) {
    val scrollState = rememberLazyListState()

    var showDialog by remember { mutableStateOf(false) }
    var taskToDelete: Task? by remember { mutableStateOf(null) }

    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = "Delete", fontSize = MaterialTheme.typography.titleLarge.fontSize)
            },
            text = {
                Text(
                    text = "Are you sure you want to remove '${taskToDelete!!.title}' task?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            },
            confirmButton = {
                Button(onClick = {
                    onDelete?.invoke(taskToDelete!!)
                    showDialog = false
                    taskToDelete = null
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        taskToDelete = null
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = {
                taskToDelete = null
                showDialog = false
            }
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = if (showActive) "Active Tasks" else "Completed Tasks",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        tasks.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen(message = it)
            },
            onSuccess = { tasks ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                    state = scrollState
                ) {
                    items(
                        items = tasks,
                        key = { task -> task.hashCode() }
                    ) { task ->
                        TaskView(
                            showActive = showActive,
                            task = task,
                            onSelect = { onSelect?.invoke(task) },
                            onComplete = { selectedTask, completed ->
                                onComplete?.invoke(selectedTask,completed)
                            },
                            onFavorite = { selectedTask, favorite ->
                                onFavorite?.invoke(selectedTask, favorite)
                            },
                            onDelete = { selectedTask ->
                                taskToDelete = selectedTask
                                showDialog = true
                            }
                        )
                    }
                }
            },
        )
    }
}