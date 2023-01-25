package com.doskoch.template.navigation.navigators

import com.doskoch.template.auth.presentation.navigation.AuthFeatureNavigator
import com.doskoch.template.navigation.MainNavigator
import javax.inject.Inject

class AuthFeatureNavigatorImpl @Inject constructor(private val navigator: MainNavigator) : AuthFeatureNavigator() {
    override fun toAnime() = navigator.toAnimeFromAuth()
}
