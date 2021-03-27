package com.doskoch.movies.features.films_all.repository.db

import com.doskoch.movies.core.components.database.DatabaseConnector
import com.doskoch.movies.database.BaseEntity
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilmDao
import com.doskoch.movies.database.modules.films.entity.DbFilm
import com.doskoch.movies.database.modules.films.entity.DbFilmDao
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.database.modules.films.view.FilmDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlin.reflect.KClass

class AllFilmsDbRepository(private val module: Module) {

    data class Module(val dbConnector: DatabaseConnector)

    fun observeChanges(): Flowable<Set<KClass<out BaseEntity>>> {
        return module.dbConnector.observeChanges(setOf(DbFilm::class, DbFavouriteFilm::class), true)
    }

    fun count(): Single<Int> {
        return module.dbConnector.select {
            Single.fromCallable { dbFilmDao().count() }
        }
    }

    fun get(limit: Int, offset: Int): Single<List<Film>> {
        return module.dbConnector.select {
            Single.fromCallable { filmDao().selectAll(limit, offset) }
        }
    }

    fun save(items: List<DbFilm>): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable { dbFilmDao().insert(items) }
        }
    }

    fun clear(): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable { dbFilmDao().clear() }
        }
    }

    fun replaceAll(items: List<DbFilm>): Completable {
        return module.dbConnector.modifyInTransaction(listOf(clear(), save(items)))
    }

    fun switchFavourite(item: Film): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable {
                if (item.isFavourite) {
                    dbFavouriteFilmDao().delete(item.id)
                } else {
                    with(item) {
                        dbFavouriteFilmDao().insert(DbFavouriteFilm(id, posterPath, title, overview, releaseDate))
                    }
                }
            }
        }
    }
}