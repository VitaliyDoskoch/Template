package com.doskoch.template.authorization.di

import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.store.AuthorizationDataStore

interface AuthorizationFeature {
    val navigator: AuthorizationFeatureNavigator
    val globalErrorHandler: GlobalErrorHandler
    val authorizationDataStore: AuthorizationDataStore
}