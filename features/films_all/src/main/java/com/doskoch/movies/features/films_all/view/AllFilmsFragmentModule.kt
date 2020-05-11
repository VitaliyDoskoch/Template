package com.doskoch.movies.features.films_all.view

import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.functions.shareFilm
import com.doskoch.movies.features.films_all.viewModel.AllFilmsViewModel
import com.doskoch.movies.features.films_all.viewModel.AllFilmsViewModelModule
import com.extensions.lifecycle.components.TypeSafeViewModelFactory

class AllFilmsFragmentModule(
    val viewModelFactory: TypeSafeViewModelFactory<AllFilmsViewModel>,
    val shareFilm: (AllFilmsFragment, Film, (Throwable) -> Unit) -> Unit
) {
    companion object {
        fun create(): AllFilmsFragmentModule {
            return AllFilmsFragmentModule(
                viewModelFactory = object : TypeSafeViewModelFactory<AllFilmsViewModel>() {
                    override fun create(): AllFilmsViewModel {
                        return AllFilmsViewModel(AllFilmsViewModelModule.create())
                    }
                },
                shareFilm = BaseFragment::shareFilm
            )
        }
    }
}