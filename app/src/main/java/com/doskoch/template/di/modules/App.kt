package com.doskoch.template.di.modules

import android.app.Application
import com.doskoch.template.GlobalErrorHandlerHolder
import com.doskoch.template.core.data.store.AuthorizationDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val navigator = MainNavigator()
    override val globalErrorHandlerHolder = GlobalErrorHandlerHolder()
    override val authorizationDataStore = AuthorizationDataStore(application)
    override val appDatabase: AppDatabase = AppDatabase.buildDatabase(application)
}