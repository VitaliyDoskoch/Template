package com.doskoch.movies.features.films_favourite.repository.db

import com.doskoch.movies.core.components.database.DatabaseConnector
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilmDao
import com.doskoch.movies.database.modules.films.view.FilmDao
import com.doskoch.movies.features.films_favourite.Injector

class FavouriteFilmsDbRepositoryModule(
    val dbConnector: DatabaseConnector,
    val dbFavouriteFilmDao: DbFavouriteFilmDao,
    val filmDao: FilmDao
) {
    companion object {
        fun create(): FavouriteFilmsDbRepositoryModule {
            return FavouriteFilmsDbRepositoryModule(
                dbConnector = DatabaseConnector(Injector.component.database),
                dbFavouriteFilmDao = Injector.component.database.dbFavouriteFilmDao(),
                filmDao = Injector.component.database.filmDao()
            )
        }
    }
}