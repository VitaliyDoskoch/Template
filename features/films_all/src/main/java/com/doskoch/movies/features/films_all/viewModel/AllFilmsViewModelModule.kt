package com.doskoch.movies.features.films_all.viewModel

import android.content.Context
import androidx.paging.PagedList
import com.doskoch.apis.the_movie_db.config.MIN_PAGE_KEY
import com.doskoch.movies.features.films.config.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films_all.Injector
import com.doskoch.movies.features.films_all.dataSource.AllFilmsDataSource
import com.doskoch.movies.features.films_all.dataSource.AllFilmsDataSourceModule
import com.doskoch.movies.features.films_all.repository.AllFilmsApiToDbConverter
import com.doskoch.movies.features.films_all.repository.api.AllFilmsApiRepository
import com.doskoch.movies.features.films_all.repository.api.AllFilmsApiRepositoryModule
import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepository
import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepositoryModule

class AllFilmsViewModelModule(
    val context: Context,
    val dbRepository: AllFilmsDbRepository,
    val apiRepository: AllFilmsApiRepository,
    val converter: AllFilmsApiToDbConverter,
    val pagedListConfig: PagedList.Config,
    val minPageKey: Int
) {
    companion object {
        fun create(): AllFilmsViewModelModule {
            return AllFilmsViewModelModule(
                context = Injector.component.context,
                dbRepository = AllFilmsDbRepository(AllFilmsDbRepositoryModule.create()),
                apiRepository = AllFilmsApiRepository(AllFilmsApiRepositoryModule.create()),
                converter = AllFilmsApiToDbConverter(),
                pagedListConfig = PAGED_LIST_CONFIG,
                minPageKey = MIN_PAGE_KEY
            )
        }
    }

    fun createDataSource(totalCount: Int,
                         onLoadRangeError: (throwable: Throwable) -> Unit): AllFilmsDataSource {
        return AllFilmsDataSource(AllFilmsDataSourceModule.create(totalCount, onLoadRangeError))
    }
}