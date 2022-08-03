package com.doskoch.template.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AbstractDimensions(
    val space_small_x7: Dp,
    val space_small_x6: Dp,
    val space_small_x5: Dp,
    val space_small_x4: Dp,
    val space_small_x3: Dp,
    val space_small_x2: Dp,
    val space_small_x1: Dp,

    val space_medium: Dp,

    val space_large_x1: Dp,
    val space_large_x2: Dp,
    val space_large_x3: Dp,
    val space_large_x4: Dp,
    val space_large_x5: Dp,
    val space_large_x6: Dp,
    val space_large_x7: Dp,

    val icon_small_x3: Dp,
    val icon_small_x2: Dp,
    val icon_small_x1: Dp,

    val icon_medium: Dp,

    val icon_large_x1: Dp,
    val icon_large_x2: Dp,
    val icon_large_x3: Dp,
    val icon_large_x4: Dp,
)

val NormalDimensions = AbstractDimensions(
    space_small_x7 = 2.dp,
    space_small_x6 = 4.dp,
    space_small_x5 = 6.dp,
    space_small_x4 = 8.dp,
    space_small_x3 = 10.dp,
    space_small_x2 = 12.dp,
    space_small_x1 = 14.dp,

    space_medium = 16.dp,

    space_large_x1 = 20.dp,
    space_large_x2 = 24.dp,
    space_large_x3 = 28.dp,
    space_large_x4 = 32.dp,
    space_large_x5 = 40.dp,
    space_large_x6 = 48.dp,
    space_large_x7 = 56.dp,

    icon_small_x3 = 12.dp,
    icon_small_x2 = 16.dp,
    icon_small_x1 = 20.dp,

    icon_medium = 24.dp,

    icon_large_x1 = 28.dp,
    icon_large_x2 = 32.dp,
    icon_large_x3 = 36.dp,
    icon_large_x4 = 40.dp
)

val SmallDimensions = AbstractDimensions(
    space_small_x7 = 2.dp,
    space_small_x6 = 4.dp,
    space_small_x5 = 6.dp,
    space_small_x4 = 8.dp,
    space_small_x3 = 10.dp,
    space_small_x2 = 12.dp,
    space_small_x1 = 14.dp,

    space_medium = 16.dp,

    space_large_x1 = 18.dp,
    space_large_x2 = 20.dp,
    space_large_x3 = 24.dp,
    space_large_x4 = 28.dp,
    space_large_x5 = 32.dp,
    space_large_x6 = 36.dp,
    space_large_x7 = 40.dp,

    icon_small_x3 = 12.dp,
    icon_small_x2 = 16.dp,
    icon_small_x1 = 20.dp,

    icon_medium = 24.dp,

    icon_large_x1 = 26.dp,
    icon_large_x2 = 28.dp,
    icon_large_x3 = 30.dp,
    icon_large_x4 = 32.dp
)

val Dimensions: AbstractDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current!!

private val LocalDimensions = staticCompositionLocalOf<AbstractDimensions?> { null }

@Composable
fun WithDimensions(content: @Composable () -> Unit) {
    val dimensions = if (LocalConfiguration.current.screenWidthDp <= 360) SmallDimensions else NormalDimensions
    CompositionLocalProvider(LocalDimensions provides dimensions) { content() }
}