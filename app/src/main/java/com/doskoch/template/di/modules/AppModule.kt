package com.doskoch.template.di.modules

import android.app.Application
import com.doskoch.template.core.store.AuthDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.di.AppComponent
import com.doskoch.template.error.GlobalErrorHandlerImpl
import com.doskoch.template.navigation.MainNavigator

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val navigator = MainNavigator()
    override val globalErrorHandler = GlobalErrorHandlerImpl()

    override val authDataStore by lazy { AuthDataStore(application) }
    override val appDatabase by lazy { AppDatabase.buildDatabase(application) }
}
