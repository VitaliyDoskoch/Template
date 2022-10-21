package com.doskoch.template.core.components.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AbstractDimensions(
    val space_2: Dp,
    val space_4: Dp,
    val space_6: Dp,
    val space_8: Dp,
    val space_10: Dp,
    val space_12: Dp,
    val space_14: Dp,
    val space_16: Dp,
    val space_20: Dp,
    val space_24: Dp,
    val space_28: Dp,
    val space_32: Dp,
    val space_40: Dp,
    val space_48: Dp,
    val space_56: Dp,
    val space_64: Dp,
    val space_72: Dp,

    val icon_12: Dp,
    val icon_16: Dp,
    val icon_20: Dp,
    val icon_24: Dp,
    val icon_28: Dp,
    val icon_32: Dp,
    val icon_40: Dp,

    val button_height: Dp
)

val NormalDimensions = AbstractDimensions(
    space_2 = 2.dp,
    space_4 = 4.dp,
    space_6 = 6.dp,
    space_8 = 8.dp,
    space_10 = 10.dp,
    space_12 = 12.dp,
    space_14 = 14.dp,
    space_16 = 16.dp,
    space_20 = 20.dp,
    space_24 = 24.dp,
    space_28 = 28.dp,
    space_32 = 32.dp,
    space_40 = 40.dp,
    space_48 = 48.dp,
    space_56 = 56.dp,
    space_64 = 64.dp,
    space_72 = 72.dp,

    icon_12 = 12.dp,
    icon_16 = 16.dp,
    icon_20 = 20.dp,
    icon_24 = 24.dp,
    icon_28 = 28.dp,
    icon_32 = 32.dp,
    icon_40 = 40.dp,

    button_height = 52.dp
)

val SmallDimensions = AbstractDimensions(
    space_2 = 2.dp,
    space_4 = 4.dp,
    space_6 = 6.dp,
    space_8 = 8.dp,
    space_10 = 10.dp,
    space_12 = 12.dp,
    space_14 = 14.dp,
    space_16 = 16.dp,
    space_20 = 18.dp,
    space_24 = 20.dp,
    space_28 = 24.dp,
    space_32 = 28.dp,
    space_40 = 32.dp,
    space_48 = 36.dp,
    space_56 = 40.dp,
    space_64 = 48.dp,
    space_72 = 56.dp,

    icon_12 = 12.dp,
    icon_16 = 16.dp,
    icon_20 = 20.dp,
    icon_24 = 24.dp,
    icon_28 = 26.dp,
    icon_32 = 28.dp,
    icon_40 = 30.dp,

    button_height = 48.dp
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
