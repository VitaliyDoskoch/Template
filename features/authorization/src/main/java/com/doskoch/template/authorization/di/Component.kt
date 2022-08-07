package com.doskoch.template.authorization.di

import com.doskoch.template.authorization.AuthorizationNestedNavigator
import com.doskoch.template.core.error.GlobalErrorHandler

interface AuthorizationFeature {
    val featureNavigator: AuthorizationFeatureNavigator
    val nestedNavigator: AuthorizationNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AuthorizationFeatureRepository
}

interface AuthorizationFeatureNavigator {
    fun toAnime()
}

interface AuthorizationFeatureRepository {

    suspend fun authorize()

}