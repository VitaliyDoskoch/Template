package com.doskoch.template.di

import android.app.Application
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.store.AuthDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.navigation.MainNavigator

interface AppComponent {
    val application: Application
    val navigator: MainNavigator
    val globalErrorHandler: GlobalErrorHandler
    val authDataStore: AuthDataStore
    val appDatabase: AppDatabase
}
