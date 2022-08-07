package com.doskoch.template.splash.di

interface SplashFeature {
    val featureNavigator: SplashFeatureNavigator
    val repository: SplashFeatureRepository
}

interface SplashFeatureNavigator {
    fun toAuthorization()
    fun toAnime()
}

interface SplashFeatureRepository {
    suspend fun authorized(): Boolean
}