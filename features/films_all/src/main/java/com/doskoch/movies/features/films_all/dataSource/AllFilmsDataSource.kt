package com.doskoch.movies.features.films_all.dataSource

import com.doskoch.movies.core.components.ui.base.recyclerView.paging.dataSource.BasePositionalDataSource
import com.doskoch.movies.database.modules.films.view.Film

class AllFilmsDataSource(private val module: AllFilmsDataSourceModule) :
    BasePositionalDataSource<Film>() {

    override val totalCount: Int = module.totalCount

    override fun get(limit: Int, offset: Int): List<Film> {
        return module.dbRepository.get(limit, offset).blockingGet()
    }

    override fun onLoadRangeError(throwable: Throwable) {
        module.onLoadRangeError(throwable)
    }
}