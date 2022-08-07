package com.doskoch.template.di

import android.app.Application
import com.doskoch.template.GlobalErrorHandlerHolder
import com.doskoch.template.core.data.store.AuthorizationDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.navigation.MainNavigator

interface AppComponent {
    val application: Application
    val navigator: MainNavigator
    val globalErrorHandlerHolder: GlobalErrorHandlerHolder
    val authorizationDataStore: AuthorizationDataStore
    val appDatabase: AppDatabase
}