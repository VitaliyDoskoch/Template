package com.doskoch.template.dataSources

import com.doskoch.template.core.data.store.AuthorizationDataStore
import com.doskoch.template.splash.di.SplashFeatureDataSource
import kotlinx.coroutines.flow.firstOrNull

class SplashFeatureDataSourceImpl(
    private val dataStore: AuthorizationDataStore
) : SplashFeatureDataSource {

    override suspend fun authorized() = dataStore.authorized().firstOrNull() ?: false
}