package com.doskoch.template.core.components.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val BasicLightColors = lightColors(
    primary = Color(0xFF9C27B0),
    primaryVariant = Color(0xFFBA68C8),
    secondary = Color(0xFFFFCA28)
)

val BasicDarkColors = darkColors()

val BasicLightExtraColors = ExtraColors(
    tertiary = Color.Red
)

val BasicDarkExtraColors = ExtraColors(
    tertiary = Color.Red
)