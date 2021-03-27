package com.doskoch.movies.features.films_favourite.dataSource

import com.doskoch.movies.core.components.ui.base.recyclerView.paging.dataSource.BasePositionalDataSource
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepository

class FavouriteFilmsDataSource(private val module: Module) : BasePositionalDataSource<Film>() {

    data class Module(
        val totalCount: Int,
        val onLoadRangeError: (throwable: Throwable) -> Unit,
        val dbRepository: FavouriteFilmsDbRepository
    )

    override val totalCount: Int = module.totalCount

    override fun get(limit: Int, offset: Int): List<Film> {
        return module.dbRepository.get(limit, offset).blockingGet()
    }

    override fun onLoadRangeError(throwable: Throwable) {
        module.onLoadRangeError(throwable)
    }
}