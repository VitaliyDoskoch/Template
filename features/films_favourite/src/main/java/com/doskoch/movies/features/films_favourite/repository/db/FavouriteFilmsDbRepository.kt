package com.doskoch.movies.features.films_favourite.repository.db

import com.doskoch.movies.database.BaseEntity
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.view.Film
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlin.reflect.KClass

class FavouriteFilmsDbRepository(private val module: FavouriteFilmsDbRepositoryModule) {

    fun observeChanges(): Flowable<Set<KClass<out BaseEntity>>> {
        return module.dbConnector.observeChanges(setOf(DbFavouriteFilm::class), true)
    }

    fun count(): Single<Int> {
        return module.dbConnector.select {
            Single.fromCallable { module.dbFavouriteFilmDao.count() }
        }
    }

    fun get(limit: Int, offset: Int): Single<List<Film>> {
        return module.dbConnector.select {
            Single.fromCallable { module.filmDao.selectFavourite(limit, offset) }
        }
    }

    fun delete(item: Film): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable { module.dbFavouriteFilmDao.delete(item.id) }
        }
    }
}