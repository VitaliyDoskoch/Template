package com.doskoch.template.splash.di

interface SplashFeature {
    val navigator: SplashFeatureNavigator
    val dataSource: SplashFeatureDataSource
}

interface SplashFeatureNavigator {
    fun toAuthorization()
    fun toAnime()
}

interface SplashFeatureDataSource {
    suspend fun authorized(): Boolean
}