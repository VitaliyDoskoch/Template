package com.doskoch.movies.features.films_all

import com.doskoch.apis.the_movie_db.config.MIN_PAGE_KEY
import com.doskoch.movies.core.components.database.DatabaseConnector
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.features.films.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films.functions.shareFilm
import com.doskoch.movies.features.films_all.dataSource.AllFilmsDataSource
import com.doskoch.movies.features.films_all.repository.AllFilmsApiToDbConverter
import com.doskoch.movies.features.films_all.repository.api.AllFilmsApiRepository
import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepository
import com.doskoch.movies.features.films_all.view.AllFilmsFragment
import com.doskoch.movies.features.films_all.viewModel.AllFilmsViewModel
import com.extensions.lifecycle.functions.typeSafeViewModelFactory

fun allFilmsFragmentDependencies() = AllFilmsFragment.Dependencies(
    typeSafeViewModelFactory { AllFilmsViewModel(allFilmsViewModelDependencies()) },
    BaseFragment<*>::shareFilm
)

fun allFilmsViewModelDependencies(): AllFilmsViewModel.Dependencies {
    val dbRepository = AllFilmsDbRepository(AllFilmsDbRepository.Module(DatabaseConnector(Injector.database)))
    return AllFilmsViewModel.Dependencies(
        Injector.context,
        dbRepository,
        AllFilmsApiRepository(AllFilmsApiRepository.Dependencies(Injector.discoverServiceConnector)),
        AllFilmsApiToDbConverter(),
        PAGED_LIST_CONFIG,
        MIN_PAGE_KEY
    ) { totalCount, onLoadRangeError -> AllFilmsDataSource(AllFilmsDataSource.Dependencies(totalCount, onLoadRangeError, dbRepository)) }
}