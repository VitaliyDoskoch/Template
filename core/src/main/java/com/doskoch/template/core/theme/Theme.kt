package com.doskoch.template.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.doskoch.template.core.R

@Composable
fun BaseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = BaseTypography,
        content = content
    )
}

