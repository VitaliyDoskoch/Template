package com.doskoch.template.di.modules

import com.doskoch.template.auth.di.AuthFeatureComponent
import com.doskoch.template.auth.di.AuthFeatureScope
import com.doskoch.template.auth.di.authFeatureComponentHolder
import com.doskoch.template.auth.navigation.AuthFeatureNavigator
import com.doskoch.template.navigation.MainNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AuthFeatureScope
class AuthFeatureNavigatorImpl @Inject constructor(private val navigator: MainNavigator) : AuthFeatureNavigator() {
    override fun toAnime() = navigator.toAnimeFromAuth()
}

@Module
@InstallIn(AuthFeatureComponent::class)
interface Mod {
    @Binds
    fun navigator(impl: AuthFeatureNavigatorImpl): AuthFeatureNavigator
}

@Module
@InstallIn(ViewModelComponent::class)
object Bridge {
    @Provides
    fun navigator() = EntryPoints.get(authFeatureComponentHolder!!.value, AuthFeatureComponent.ComponentEntryPoint::class.java).navigator()
}