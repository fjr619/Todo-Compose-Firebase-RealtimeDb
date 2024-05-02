package com.fjr619.composefirebasedb.ui.screens.home.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import com.fjr619.composefirebasedb.domain.model.RequestState

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun <T> RequestState<T>.DisplayResult(
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (String) -> Unit,
    transitionSpec: AnimatedContentTransitionScope<*>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 300)) togetherWith
                fadeOut(tween(durationMillis = 300))
    }
) {

    AnimatedContent(
        targetState = this,
        label = "",
        transitionSpec = transitionSpec,
        contentKey = { this::class.java }
    ) {
        when(it) {
            is RequestState.Idle -> {
                onIdle?.invoke()
            }
            is RequestState.Loading -> {
                onLoading()
            }
            is RequestState.Error -> {
                onError(getErrorMessage())
            }
            is RequestState.Success -> {
                onSuccess(getSuccessData())
            }
        }
    }
}