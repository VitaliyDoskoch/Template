package com.doskoch.template.core.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtraColors(
    val tertiary: Color
)

val MaterialTheme.extraColors: ExtraColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtraColors.current!!

private val LocalExtraColors = staticCompositionLocalOf<ExtraColors?> { null }

@Composable
fun BasicTheme(content: @Composable () -> Unit) {
    val extraColors = if (isSystemInDarkTheme()) BasicDarkExtraColors else BasicLightExtraColors

    CompositionLocalProvider(LocalExtraColors provides extraColors) {
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) BasicDarkColors else BasicLightColors,
            typography = BasicTypography,
            shapes = BasicShapes,
            content = content
        )
    }
}
