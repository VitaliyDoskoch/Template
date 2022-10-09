package com.doskoch.template.repositories.splash

import com.doskoch.template.data.store.AuthorizationDataStore
import com.doskoch.template.splash.di.SplashFeatureRepository
import kotlinx.coroutines.flow.firstOrNull

class SplashFeatureRepositoryImpl(
    private val dataStore: AuthorizationDataStore
) : SplashFeatureRepository {

    override suspend fun authorized() = dataStore.authorized().firstOrNull() ?: false
}