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

fun FavouriteFilmsFragment.newModule() = FavouriteFilmsFragment.Module(
    typeSafeViewModelFactory { FavouriteFilmsViewModel(newViewModelModule()) },
    BaseFragment<*>::shareFilm
)

fun FavouriteFilmsFragment.newViewModelModule(): FavouriteFilmsViewModel.Module {
    val dbRepository = FavouriteFilmsDbRepository(newDbRepositoryModule())

    return FavouriteFilmsViewModel.Module(dbRepository, PAGED_LIST_CONFIG) { totalCount, onLoadRangeError ->
        FavouriteFilmsDataSource(FavouriteFilmsDataSource.Module(totalCount, onLoadRangeError, dbRepository))
    }
}

fun FavouriteFilmsFragment.newDbRepositoryModule() = FavouriteFilmsDbRepository.Module(DatabaseConnector(Injector.database))