package com.doskoch.template.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun FadeInOnCompose(
    modifier: Modifier = Modifier,
    key: Any? = null,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    val state = remember(key) { MutableTransitionState(false).apply { targetState = true } }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
        content = content
    )
}