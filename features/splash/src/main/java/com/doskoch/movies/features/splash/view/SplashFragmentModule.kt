package com.doskoch.movies.features.splash.view

import com.doskoch.movies.features.splash.BuildConfig
import com.doskoch.movies.features.splash.Directions
import com.doskoch.movies.features.splash.Injector
import com.doskoch.movies.features.splash.viewModel.SplashViewModel
import com.doskoch.movies.features.splash.viewModel.SplashViewModelModule
import com.extensions.lifecycle.components.TypeSafeViewModelFactory

class SplashFragmentModule(
    val viewModelFactory: TypeSafeViewModelFactory<SplashViewModel>,
    val directions: Directions,
    val versionCode: Int
) {
    companion object {
        fun create(): SplashFragmentModule {
            return SplashFragmentModule(
                viewModelFactory = object : TypeSafeViewModelFactory<SplashViewModel>() {
                    override fun create(): SplashViewModel {
                        return SplashViewModel(SplashViewModelModule.create())
                    }
                },
                directions = Injector.component.directions,
                versionCode = BuildConfig.VERSION_CODE
            )
        }
    }
}