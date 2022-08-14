package com.doskoch.template.authorization.di

import com.doskoch.template.authorization.navigation.AuthorizationNestedNavigator
import com.doskoch.template.core.components.error.GlobalErrorHandler

interface AuthorizationFeature {
    val globalNavigator: AuthorizationFeatureGlobalNavigator
    val nestedNavigator: AuthorizationNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AuthorizationFeatureRepository
}

interface AuthorizationFeatureGlobalNavigator {
    fun toAnime()
}

interface AuthorizationFeatureRepository {

    suspend fun authorize()

}