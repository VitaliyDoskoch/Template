package com.doskoch.template.core.store

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataStore @Inject constructor(private val application: Application) {

    private val Application.store by preferencesDataStore("AuthDataStore")

    fun authorized() = application.store.data.map { it[AUTHORIZED] }

    suspend fun updateAuthorized(value: Boolean) = application.store.edit { it[AUTHORIZED] = value }

    companion object {
        private val AUTHORIZED = booleanPreferencesKey("AUTHORIZED")
    }
}
