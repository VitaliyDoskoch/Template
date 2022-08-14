package com.doskoch.template.splash.di

interface SplashFeature {
    val globalNavigator: SplashFeatureGlobalNavigator
    val repository: SplashFeatureRepository
}

interface SplashFeatureGlobalNavigator {
    fun toAuthorization()
    fun toAnime()
}

interface SplashFeatureRepository {
    suspend fun authorized(): Boolean
}