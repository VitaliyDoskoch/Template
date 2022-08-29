package com.doskoch.template.authorization.di

import com.doskoch.template.authorization.navigation.AuthorizationFeatureNavigator
import com.doskoch.template.core.components.error.GlobalErrorHandler

interface AuthorizationFeature {
    val navigator: AuthorizationFeatureNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AuthorizationFeatureRepository
}

interface AuthorizationFeatureRepository {

    suspend fun authorize()

}