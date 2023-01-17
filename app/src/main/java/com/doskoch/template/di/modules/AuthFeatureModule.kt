package com.doskoch.template.di.modules

import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.auth.di.AuthFeatureScope
import com.doskoch.template.auth.di.Bridged
import com.doskoch.template.auth.di.authFeatureComponentHolder
import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import com.doskoch.template.navigation.MainNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(AuthFeatureComponent::class)
object AuthFeatureModule {

    @Provides
    @AuthFeatureScope
    fun navigator(navigator: MainNavigator): AuthFeatureNavigator = object : AuthFeatureNavigator() {
        override fun toAnime() = navigator.toAnimeFromAuth()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object Bridge {
    @Provides
    @Bridged
    fun navigator() = EntryPoints.get(authFeatureComponentHolder!!.value, AuthFeatureComponent.ComponentEntryPoint::class.java).navigator()
}