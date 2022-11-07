package com.doskoch.template.auth.di

import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import com.doskoch.template.core.components.error.GlobalErrorHandler

interface AuthFeatureComponent {
    val navigator: AuthFeatureNavigator
    val globalErrorHandler: GlobalErrorHandler
}
