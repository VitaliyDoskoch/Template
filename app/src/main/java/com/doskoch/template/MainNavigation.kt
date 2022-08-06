package com.doskoch.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainNavigation() {
    Scaffold() {
        Box(Modifier.fillMaxSize()
            .background(Color.Red))
    }
}