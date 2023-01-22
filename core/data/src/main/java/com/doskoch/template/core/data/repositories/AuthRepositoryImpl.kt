package com.doskoch.template.core.data.repositories

import com.doskoch.template.core.data.store.AuthDataStore
import com.doskoch.template.core.domain.auth.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataStore: AuthDataStore) : AuthRepository {
    override suspend fun isAuthorized() = authDataStore.isAuthorized().firstOrNull() ?: false

    override suspend fun updateIsAuthorized(isAuthorized: Boolean) {
        authDataStore.updateIsAuthorized(isAuthorized)
    }
}
