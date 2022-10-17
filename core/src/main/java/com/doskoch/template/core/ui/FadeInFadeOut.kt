package com.doskoch.template.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun FadeInFadeOut(modifier: Modifier = Modifier, key: Any? = null, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visibleState = remember(key) { MutableTransitionState(false).apply { targetState = true } },
        enter = fadeIn(tween(1000)),
        exit = fadeOut(),
        modifier = modifier,
        content = content
    )

    DisposableEffect(key1 = key) {
        this.onDispose {  }
    }
}