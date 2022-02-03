package com.doskoch.movies.features.films_favourite

import com.doskoch.movies.core.components.database.DatabaseConnector
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.features.films.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films.functions.shareFilm
import com.doskoch.movies.features.films_favourite.dataSource.FavouriteFilmsDataSource
import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepository
import com.doskoch.movies.features.films_favourite.view.FavouriteFilmsFragment
import com.doskoch.movies.features.films_favourite.viewModel.FavouriteFilmsViewModel
import com.extensions.lifecycle.functions.typeSafeViewModelFactory

fun favouriteFilmsDependencies() = FavouriteFilmsFragment.Dependencies(
    typeSafeViewModelFactory { FavouriteFilmsViewModel(favouriteFilmsViewModelDependencies()) },
    BaseFragment<*>::shareFilm
)

fun favouriteFilmsViewModelDependencies(): FavouriteFilmsViewModel.Dependencies {
    val dbRepository = FavouriteFilmsDbRepository(dbRepositoryDependencies())

    return FavouriteFilmsViewModel.Dependencies(dbRepository, PAGED_LIST_CONFIG) { totalCount, onLoadRangeError ->
        FavouriteFilmsDataSource(FavouriteFilmsDataSource.Module(totalCount, onLoadRangeError, dbRepository))
    }
}

fun dbRepositoryDependencies() = FavouriteFilmsDbRepository.Dependencies(DatabaseConnector(Injector.database))