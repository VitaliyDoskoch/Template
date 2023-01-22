package com.doskoch.template.navigation.navigators

import com.doskoch.template.auth.presentation.di.AuthFeatureScope
import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator
import com.doskoch.template.navigation.MainNavigator
import javax.inject.Inject

@com.doskoch.template.auth.presentation.di.AuthFeatureScope
class AuthFeatureNavigatorImpl @Inject constructor(private val navigator: MainNavigator) : com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator() {
    override fun toAnime() = navigator.toAnimeFromAuth()
}
