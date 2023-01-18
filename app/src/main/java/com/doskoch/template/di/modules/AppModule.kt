package com.doskoch.template.di.modules

import android.app.Application
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.store.AuthDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.di.AppComponent
import com.doskoch.template.di.AppInjector
import com.doskoch.template.error.GlobalErrorHandlerImpl
import com.doskoch.template.navigation.MainNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val navigator = MainNavigator()
    override val globalErrorHandler = GlobalErrorHandlerImpl()

    override val authDataStore by lazy { AuthDataStore(application) }
    override val appDatabase by lazy { AppDatabase.buildDatabase(application) }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // TODO: refactor
    @Provides
    fun globalErrorHandlerImpl(appInjector: AppInjector) = appInjector.component.globalErrorHandler as GlobalErrorHandlerImpl

    @Provides
    @Singleton
    fun appDatabase(application: Application) = AppDatabase.buildDatabase(application)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Binder {
        @Binds
        abstract fun globalErrorHandler(impl: GlobalErrorHandlerImpl): GlobalErrorHandler
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Provider {
        fun mainNavigator(): MainNavigator
    }
}
