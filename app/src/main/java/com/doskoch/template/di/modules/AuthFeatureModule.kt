package com.doskoch.template.di.modules

import com.doskoch.template.auth.presentation.di.AuthFeatureComponent
import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator
import com.doskoch.template.navigation.navigators.AuthFeatureNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn(AuthFeatureComponent::class)
interface AuthFeatureModule {
    @Binds
    fun navigator(impl: AuthFeatureNavigatorImpl): AuthFeatureNavigator
}
