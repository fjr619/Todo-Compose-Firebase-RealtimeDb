package com.fjr619.composefirebasedb.ui.screens.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun TaskScreen(
    state: TaskUiState,
    onNavigateUp: () -> Unit,
    onTaskEvent: (TaskEvent) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    //https://rivaldy.medium.com/jetpack-compose-customize-your-searchbar-with-basictextfield-c1cdcbd3e3aa
                    BasicTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        ),
                        singleLine = true,
                        value = state.currentTask.title,
                        onValueChange = {
                            onTaskEvent(TaskEvent.SetTitle(it))
                        },
                        decorationBox = {
                            if (state.currentTask.title.isEmpty()) {
                                Text(
                                    text = TaskViewModel.DEFAULT_TITLE
                                )
                            }
                            it()
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = dropUnlessResumed {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            onNavigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.currentTask.title.isNotEmpty() && state.currentTask.desc.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    modifier = Modifier,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                    onClick = dropUnlessResumed {

                        if (state.currentTask.id.isEmpty()) {
                            onTaskEvent(TaskEvent.Add)
                        } else {
                            onTaskEvent(TaskEvent.Update)
                        }

                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onNavigateUp()
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checkmark Icon"
                    )
                }
            }
        }
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .padding(horizontal = 24.dp)
                .focusRequester(focusRequester),
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            ),
            value = state.currentTask.desc,
            onValueChange = { desc ->
                onTaskEvent(TaskEvent.SetDesc(desc))
            },
            decorationBox = {
                if (state.currentTask.desc.isEmpty()) {
                    Text(
                        text = TaskViewModel.DEFAULT_DESCRIPTION,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                it()
            }
        )
    }
}