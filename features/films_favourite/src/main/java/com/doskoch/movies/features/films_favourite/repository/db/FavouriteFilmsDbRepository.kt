package com.doskoch.movies.features.films_favourite.repository.db

import com.doskoch.movies.core.components.database.DatabaseConnector
import com.doskoch.movies.database.BaseEntity
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.view.Film
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlin.reflect.KClass

class FavouriteFilmsDbRepository(private val dependencies: Dependencies) {

    data class Dependencies(val dbConnector: DatabaseConnector)

    fun observeChanges(): Flowable<Set<KClass<out BaseEntity>>> {
        return dependencies.dbConnector.observeChanges(setOf(DbFavouriteFilm::class), true)
    }

    fun count(): Single<Int> {
        return dependencies.dbConnector.select {
            Single.fromCallable { dbFavouriteFilmDao().count() }
        }
    }

    fun get(limit: Int, offset: Int): Single<List<Film>> {
        return dependencies.dbConnector.select {
            Single.fromCallable { filmDao().selectFavourite(limit, offset) }
        }
    }

    fun delete(item: Film): Completable {
        return dependencies.dbConnector.modify {
            Completable.fromCallable { dbFavouriteFilmDao().delete(item.id) }
        }
    }
}