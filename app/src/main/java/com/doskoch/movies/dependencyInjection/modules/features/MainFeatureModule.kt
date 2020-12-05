package com.doskoch.movies.dependencyInjection.modules.features

import androidx.fragment.app.Fragment
import com.doskoch.movies.R
import com.doskoch.movies.dependencyInjection.AppComponent
import com.doskoch.movies.features.films_all.view.AllFilmsFragment
import com.doskoch.movies.features.films_favourite.view.FavouriteFilmsFragment
import com.doskoch.movies.features.main.MainFeature

class MainFeatureModule(
    override val provideAllFilmsFragment: () -> Fragment,
    override val provideFavouriteFilmsFragment: () -> Fragment
) : MainFeature {
    companion object {
        fun create(component: AppComponent) = MainFeatureModule(
            provideAllFilmsFragment = { AllFilmsFragment.create(R.id.appBarLayout) },
            provideFavouriteFilmsFragment = { FavouriteFilmsFragment.create(R.id.appBarLayout) }
        )
    }
}