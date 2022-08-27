package com.doskoch.template.authorization.screens.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doskoch.legacy.android.viewModel.typedViewModelFactory
import com.doskoch.template.authorization.di.Module
import com.doskoch.template.authorization.R
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.core.components.ui.CoreButton
import com.doskoch.template.core.components.ui.CoreTextButton

@Composable
fun SignUpScreen() {
    SignUpScreen(
        vm = viewModel(factory = typedViewModelFactory { Module.signUpViewModel() })
    )
}

@Composable
private fun SignUpScreen(vm: SignUpViewModel) {
    SignUpScreen(
        onSignIn = vm::onSignIn,
        onSkip = vm::onSkip
    )
}

@Composable
private fun SignUpScreen(
    onSignIn: () -> Unit,
    onSkip: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .systemBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.im_app_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = Dimensions.space_24)
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

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.project_description),
                modifier = Modifier
                    .padding(start = Dimensions.space_16, end = Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.sign_in_label),
                modifier = Modifier
                    .padding(start = Dimensions.space_16, end = Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            CoreButton(
                text = stringResource(R.string.sign_in),
                onClick = onSignIn,
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_24, end = Dimensions.space_16)
                    .fillMaxWidth()
                    .requiredHeight(Dimensions.button_height)
            )

            Text(
                text = stringResource(R.string.or),
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_8, end = Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            CoreTextButton(
                text = stringResource(R.string.skip),
                onClick = onSkip,
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_8, end = Dimensions.space_16, bottom = Dimensions.space_16)
                    .fillMaxWidth()
                    .requiredHeight(Dimensions.button_height)
            )
        }
    }
}