package com.doskoch.template.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doskoch.legacy.android.viewModel.viewModelFactory

@Composable
fun SplashScreen() {
    SplashScreen(
        vm = viewModel(factory = viewModelFactory { Module.splashViewModel })
    )
}

@Composable
fun SplashScreen(vm: SplashViewModel) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Text(text = vm.text)
        }
    }
}