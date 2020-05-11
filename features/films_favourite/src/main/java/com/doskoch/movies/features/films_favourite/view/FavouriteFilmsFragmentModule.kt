package com.doskoch.movies.features.films_favourite.view

import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.functions.shareFilm
import com.doskoch.movies.features.films_favourite.viewModel.FavouriteFilmsViewModel
import com.doskoch.movies.features.films_favourite.viewModel.FavouriteFilmsViewModelModule
import com.extensions.lifecycle.components.TypeSafeViewModelFactory

class FavouriteFilmsFragmentModule(
    val viewModelFactory: TypeSafeViewModelFactory<FavouriteFilmsViewModel>,
    val shareFilm: (FavouriteFilmsFragment, Film, (Throwable) -> Unit) -> Unit
) {
    companion object {
        fun create(): FavouriteFilmsFragmentModule {
            return FavouriteFilmsFragmentModule(
                viewModelFactory = object : TypeSafeViewModelFactory<FavouriteFilmsViewModel>() {
                    override fun create(): FavouriteFilmsViewModel {
                        return FavouriteFilmsViewModel(FavouriteFilmsViewModelModule.create())
                    }
                },
                shareFilm = BaseFragment::shareFilm
            )
        }
    }
}