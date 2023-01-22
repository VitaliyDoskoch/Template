package com.doskoch.template.di.modules

import android.app.Application
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.error.GlobalErrorHandlerImpl
import com.doskoch.template.navigation.MainNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun globalErrorHandler(impl: GlobalErrorHandlerImpl): GlobalErrorHandler

    companion object {
        @Provides
        @Singleton
        fun appDatabase(application: Application) = AppDatabase.buildDatabase(application)
    }

    @dagger.hilt.EntryPoint
    @InstallIn(SingletonComponent::class)
    interface EntryPoint {
        fun navigator(): MainNavigator
    }
}
