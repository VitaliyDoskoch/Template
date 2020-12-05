package com.doskoch.movies.features.films_all.repository.db

import com.doskoch.movies.core.components.database.DatabaseConnector
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilmDao
import com.doskoch.movies.database.modules.films.entity.DbFilmDao
import com.doskoch.movies.database.modules.films.view.FilmDao
import com.doskoch.movies.features.films_all.Injector

class AllFilmsDbRepositoryModule(
    val dbConnector: DatabaseConnector,
    val dbFilmDao: DbFilmDao,
    val dbFavouriteFilmDao: DbFavouriteFilmDao,
    val filmDao: FilmDao
) {
    companion object {
        fun create(): AllFilmsDbRepositoryModule {
            return AllFilmsDbRepositoryModule(
                dbConnector = DatabaseConnector(Injector.database),
                dbFilmDao = Injector.database.dbFilmDao(),
                dbFavouriteFilmDao = Injector.database.dbFavouriteFilmDao(),
                filmDao = Injector.database.filmDao()
            )
        }
    }
}