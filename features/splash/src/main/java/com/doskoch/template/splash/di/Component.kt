package com.doskoch.template.splash.di

interface SplashFeature {
    val navigator: SplashFeatureNavigator
    val repository: SplashFeatureRepository
}

interface SplashFeatureNavigator {
    fun toAuthorization()
    fun toAnime()
}

interface SplashFeatureRepository {
    suspend fun authorized(): Boolean
}