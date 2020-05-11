package com.doskoch.movies.features.films_favourite.dataSource

import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepository
import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepositoryModule

class FavouriteFilmsDataSourceModule(
    val totalCount: Int,
    val onLoadRangeError: (throwable: Throwable) -> Unit,
    val dbRepository: FavouriteFilmsDbRepository
) {
    companion object {
        fun create(totalCount: Int,
                   onLoadRangeError: (throwable: Throwable) -> Unit): FavouriteFilmsDataSourceModule {
            return FavouriteFilmsDataSourceModule(
                totalCount = totalCount,
                onLoadRangeError = onLoadRangeError,
                dbRepository = FavouriteFilmsDbRepository(FavouriteFilmsDbRepositoryModule.create())
            )
        }
    }
}