package com.doskoch.template.di

import android.app.Application
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.store.AuthorizationDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.navigation.MainNavigator

interface AppComponent {
    val application: Application
    val navigator: MainNavigator
    val globalErrorHandler: GlobalErrorHandler
    val authorizationDataStore: AuthorizationDataStore
    val appDatabase: AppDatabase
}
