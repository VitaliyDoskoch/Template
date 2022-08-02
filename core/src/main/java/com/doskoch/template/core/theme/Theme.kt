package com.doskoch.template.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun BasicTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if(isSystemInDarkTheme()) BasicDarkColors else BasicLightColors,
        typography = BasicTypography,
        shapes = BasicShapes,
        content = content
    )
}

