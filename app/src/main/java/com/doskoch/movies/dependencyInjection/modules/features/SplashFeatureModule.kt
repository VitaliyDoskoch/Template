package com.doskoch.movies.dependencyInjection.modules.features

import androidx.navigation.NavDirections
import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.splash.SplashFeatureComponent
import com.doskoch.movies.features.splash.SplashFeatureDirections
import com.doskoch.movies.features.splash.view.SplashFragmentDirections

class SplashFeatureModule(
    override val directions: SplashFeatureDirections
) : SplashFeatureComponent {
    companion object {
        fun create(component: AppComponent): SplashFeatureModule {
            return SplashFeatureModule(
                directions = provideNavDirections()
            )
        }

        private fun provideNavDirections(): SplashFeatureDirections {
            return object : SplashFeatureDirections {
                override fun toMain(): NavDirections {
                    return SplashFragmentDirections.toMain()
                }
            }
        }
    }
}