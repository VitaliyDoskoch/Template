package com.doskoch.movies.features.films_all.dataSource

import com.doskoch.movies.core.components.ui.base.recyclerView.paging.dataSource.BasePositionalDataSource
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepository

class AllFilmsDataSource(private val dependencies: Dependencies) : BasePositionalDataSource<Film>() {

    data class Dependencies(
        val totalCount: Int,
        val onLoadRangeError: (throwable: Throwable) -> Unit,
        val dbRepository: AllFilmsDbRepository
    )

    override val totalCount: Int = dependencies.totalCount

    override fun get(limit: Int, offset: Int): List<Film> {
        return dependencies.dbRepository.get(limit, offset).blockingGet()
    }

    override fun onLoadRangeError(throwable: Throwable) {
        dependencies.onLoadRangeError(throwable)
    }
}