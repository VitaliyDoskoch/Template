package com.doskoch.template.core.domain.auth

interface AuthRepository {
    suspend fun isAuthorized(): Boolean
    suspend fun updateIsAuthorized(isAuthorized: Boolean)
}
