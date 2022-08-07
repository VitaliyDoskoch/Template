package com.doskoch.template.di

import android.app.Application
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.doskoch.template.api.jikan.JikanApi
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.features.splash.SplashNavigator
import com.doskoch.template.features.splash.SplashFeature
import com.doskoch.template.navigation.MainNavigator

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val navigator = MainNavigator()
    override val appDatabase: AppDatabase = AppDatabase.buildDatabase(application)
}

fun jikanApiModule(component: AppComponent) = object : JikanApi {}

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
    override val navigator = object : SplashNavigator {
        override fun toSignUp() = navOptions { popUpTo(MainNavigator.startDestination.name) { inclusive = true } }
            .let(component.navigator::toSignUp)
    }
}
