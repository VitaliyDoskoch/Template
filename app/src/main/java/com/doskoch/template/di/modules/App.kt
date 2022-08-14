package com.doskoch.template.di.modules

import android.app.Application
import com.doskoch.template.GlobalErrorHandlerImpl
import com.doskoch.template.api.jikan.JikanApiProvider
import com.doskoch.template.core.data.repository.AnimeRepository
import com.doskoch.template.core.data.store.AuthorizationDataStore
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.di.AppComponent
import com.doskoch.template.navigation.MainNavigator

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val navigator = MainNavigator()
    override val globalErrorHandler = GlobalErrorHandlerImpl()

    override val authorizationDataStore by lazy { AuthorizationDataStore(application) }
    override val animeRepository by lazy { AnimeRepository(JikanApiProvider.topService) }
    override val appDatabase by lazy { AppDatabase.buildDatabase(application) }
}