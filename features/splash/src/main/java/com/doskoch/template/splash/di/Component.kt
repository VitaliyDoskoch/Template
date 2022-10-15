package com.doskoch.template.splash.di

import com.doskoch.template.core.store.AuthorizationDataStore

interface SplashFeature {
    val navigator: SplashFeatureNavigator
    val authorizationDataStore: AuthorizationDataStore
}

interface SplashFeatureNavigator {
    fun toAuthorization()
    fun toAnime()
}