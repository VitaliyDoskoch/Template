package com.doskoch.movies.dependencyInjection.modules

import android.app.Application
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.dependencyInjection.AppComponent

class AppModule(
    override val application: Application,
    override val appDatabase: AppDatabase
) : AppComponent {
    companion object {
        fun create(application: Application): AppModule {
            return AppModule(
                application = application,
                appDatabase = AppDatabase.buildDatabase(application)
            )
        }
    }
}