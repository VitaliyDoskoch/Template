package com.doskoch.template.auth.screens.signIn

import com.doskoch.template.core.components.error.CoreError

data class SignInScreenState(
    val email: String,
    val error: CoreError?,
    val isProceedButtonEnabled: Boolean,
    val isLoading: Boolean,
    val actions: Actions
) {
    data class Actions(
        val onNavigateBack: () -> Unit,
        val onUpdateEmail: (String) -> Unit,
        val onProceed: () -> Unit
    )
}
