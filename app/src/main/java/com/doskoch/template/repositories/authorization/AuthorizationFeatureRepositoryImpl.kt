package com.doskoch.template.repositories.authorization

import com.doskoch.template.authorization.di.AuthorizationFeatureRepository
import com.doskoch.template.core.data.store.AuthorizationDataStore

class AuthorizationFeatureRepositoryImpl(
    private val dataStore: AuthorizationDataStore
) : AuthorizationFeatureRepository {

    override suspend fun authorize() {
        dataStore.updateAuthorized(true)
    }
}