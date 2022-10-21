package com.doskoch.template.authorization.screens.signIn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doskoch.template.authorization.R
import com.doskoch.template.authorization.di.Module
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.core.ui.CoreButton
import com.doskoch.template.core.ui.CoreTextInputField
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignInScreen(vm: SignInViewModel = viewModel { Module.signInViewModel() }) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            val systemUiController = rememberSystemUiController()
            SideEffect { systemUiController.setStatusBarColor(Color.Transparent) }

            TopAppBar(
                title = {},
                modifier = Modifier
                    .statusBarsPadding(),
                navigationIcon = {
                    IconButton(onClick = state.actions.onNavigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.desc_navigate_back)
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.log_in_with_email),
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_16, end = Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.log_in_with_email_hint),
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_72, end = Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )

            CoreTextInputField(
                value = state.email,
                onValueChange = state.actions.onUpdateEmail,
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_16, end = Dimensions.space_16)
                    .fillMaxWidth(),
                hint = stringResource(R.string.email_hint),
                errorMessage = state.error?.localizedMessage(LocalContext.current),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            CoreButton(
                text = stringResource(R.string.proceed),
                onClick = state.actions.onProceed,
                modifier = Modifier
                    .padding(Dimensions.space_16)
                    .imePadding()
                    .fillMaxWidth()
                    .requiredHeight(Dimensions.button_height),
                enabled = state.isProceedButtonEnabled,
                isLoading = state.isLoading
            )
        }
    }
}
