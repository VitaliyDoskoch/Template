package com.doskoch.template.splash.di

import com.doskoch.template.core.store.AuthorizationDataStore
import com.doskoch.template.splash.navigation.SplashFeatureNavigator

interface SplashFeatureComponent {
    val navigator: SplashFeatureNavigator
    val authorizationDataStore: AuthorizationDataStore
}
