package com.doskoch.template.core.data.store

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStore @Inject constructor(private val application: Application) {

    fun isAuthorized() = application.store.data.map { it[IS_AUTHORIZED] }

    suspend fun updateIsAuthorized(value: Boolean) = application.store.edit { it[IS_AUTHORIZED] = value }

    companion object {
        private val Application.store by preferencesDataStore("AuthDataStore")

        private val IS_AUTHORIZED = booleanPreferencesKey("IS_AUTHORIZED")
    }
}
