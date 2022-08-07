package com.doskoch.template.di

import android.app.Application
import androidx.navigation.navOptions
import com.doskoch.template.GlobalErrorHandlerHolder
import com.doskoch.template.api.jikan.JikanApi
import com.doskoch.template.authorization.AuthorizationFeature
import com.doskoch.template.authorization.AuthorizationFeatureNavigator
import com.doskoch.template.authorization.AuthorizationNestedNavigator
import com.doskoch.template.database.AppDatabase
import com.doskoch.template.features.splash.SplashFeatureNavigator
import com.doskoch.template.features.splash.SplashFeature
import com.doskoch.template.navigation.Destinations
import com.doskoch.template.navigation.MainNavigator

fun appModule(application: Application) = object : AppComponent {
    override val application = application
    override val navigator = MainNavigator()
    override val globalErrorHandlerHolder = GlobalErrorHandlerHolder()
    override val appDatabase: AppDatabase = AppDatabase.buildDatabase(application)
}

fun jikanApiModule(component: AppComponent) = object : JikanApi {}

fun splashFeatureModule(component: AppComponent) = object : SplashFeature {
    override val navigator = object : SplashFeatureNavigator {
        override fun toAuthorization() = navOptions { popUpTo(MainNavigator.startDestination.name) { inclusive = true } }
            .let(component.navigator::toAuthorization)
    }
}

fun authorizationFeatureModule(component: AppComponent) = object : AuthorizationFeature {
    override val navigator = object : AuthorizationFeatureNavigator {
        override fun toMain() = navOptions { popUpTo(Destinations.Authorization.name) { inclusive = true } }
            .let(component.navigator::toMain)
    }

    override val nestedNavigator = AuthorizationNestedNavigator()

    override val globalErrorHandler = component.globalErrorHandlerHolder.handler
}