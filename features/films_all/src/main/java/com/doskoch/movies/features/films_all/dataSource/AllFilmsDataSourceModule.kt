package com.doskoch.movies.features.films_all.dataSource

import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepository
import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepositoryModule

class AllFilmsDataSourceModule(
    val totalCount: Int,
    val onLoadRangeError: (throwable: Throwable) -> Unit,
    val dbRepository: AllFilmsDbRepository
) {
    companion object {
        fun create(totalCount: Int,
                   onLoadRangeError: (throwable: Throwable) -> Unit): AllFilmsDataSourceModule {
            return AllFilmsDataSourceModule(
                totalCount = totalCount,
                onLoadRangeError = onLoadRangeError,
                dbRepository = AllFilmsDbRepository(AllFilmsDbRepositoryModule.create())
            )
        }
    }
}