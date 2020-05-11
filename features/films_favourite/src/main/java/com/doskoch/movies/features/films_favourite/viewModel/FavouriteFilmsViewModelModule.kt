package com.doskoch.movies.features.films_favourite.viewModel

import androidx.paging.PagedList
import com.doskoch.movies.features.films.config.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films_favourite.dataSource.FavouriteFilmsDataSource
import com.doskoch.movies.features.films_favourite.dataSource.FavouriteFilmsDataSourceModule
import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepository
import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepositoryModule

class FavouriteFilmsViewModelModule(
    val dbRepository: FavouriteFilmsDbRepository,
    val pagedListConfig: PagedList.Config
) {
    companion object {
        fun create(): FavouriteFilmsViewModelModule {
            return FavouriteFilmsViewModelModule(
                dbRepository = FavouriteFilmsDbRepository(FavouriteFilmsDbRepositoryModule.create()),
                pagedListConfig = PAGED_LIST_CONFIG
            )
        }
    }

    fun createDataSource(totalCount: Int,
                         onLoadRangeError: (throwable: Throwable) -> Unit): FavouriteFilmsDataSource {
        return FavouriteFilmsDataSource(
            FavouriteFilmsDataSourceModule.create(totalCount, onLoadRangeError)
        )
    }
}