package com.doskoch.template.splash.screens.splash

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.splash.R
import com.doskoch.template.splash.di.Module

@Composable
fun SplashScreen(vm: SplashViewModel = viewModel { Module.splashViewModel() }) {
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
                    .padding(start = Dimensions.space_16, end = Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = LocalContext.current.run {
                    stringResource(R.string.app_version, packageManager.getPackageInfo(packageName, 0).versionName)
                },
                modifier = Modifier
                    .padding(start = Dimensions.space_16, end = Dimensions.space_16)
                    .wrapContentWidth()
                    .background(color = MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.large)
                    .padding(horizontal = Dimensions.space_16, vertical = Dimensions.space_4),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.developed_by),
                modifier = Modifier
                    .padding(
                        start = Dimensions.space_16,
                        top = Dimensions.space_16,
                        end = Dimensions.space_16,
                        bottom = Dimensions.space_24
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}