package com.doskoch.template.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doskoch.legacy.android.viewModel.viewModelFactory
import com.doskoch.template.core.theme.Dimensions

@Composable
fun SplashScreen() {
    SplashScreen(
        vm = viewModel(factory = viewModelFactory { Module.splashViewModel })
    )
}

@Composable
fun SplashScreen(vm: SplashViewModel) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .systemBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.im_app_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
            )

            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(start = Dimensions.space_medium, end = Dimensions.space_medium)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = LocalContext.current.run {
                    stringResource(R.string.version, packageManager.getPackageInfo(packageName, 0).versionName)
                },
                modifier = Modifier
                    .padding(start = Dimensions.space_medium, end = Dimensions.space_medium)
                    .wrapContentWidth()
                    .background(color = MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.large)
                    .padding(horizontal = Dimensions.space_medium, vertical = Dimensions.space_small_x6),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.developed_by),
                modifier = Modifier
                    .padding(
                        start = Dimensions.space_medium,
                        top = Dimensions.space_medium,
                        end = Dimensions.space_medium,
                        bottom = Dimensions.space_large_x2
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}