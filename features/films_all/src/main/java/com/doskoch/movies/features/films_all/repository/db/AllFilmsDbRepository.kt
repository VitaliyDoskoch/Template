package com.doskoch.movies.features.films_all.repository.db

import com.doskoch.movies.database.BaseEntity
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.entity.DbFilm
import com.doskoch.movies.database.modules.films.view.Film
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlin.reflect.KClass

class AllFilmsDbRepository(private val module: AllFilmsDbRepositoryModule) {

    fun observeChanges(): Flowable<Set<KClass<out BaseEntity>>> {
        return module.dbConnector.observeChanges(setOf(DbFilm::class, DbFavouriteFilm::class), true)
    }

    fun count(): Single<Int> {
        return module.dbConnector.select {
            Single.fromCallable { module.dbFilmDao.count() }
        }
    }

    fun get(limit: Int, offset: Int): Single<List<Film>> {
        return module.dbConnector.select {
            Single.fromCallable { module.filmDao.selectAll(limit, offset) }
        }
    }

    fun save(items: List<DbFilm>): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable { module.dbFilmDao.insert(items) }
        }
    }

    fun clear(): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable { module.dbFilmDao.clear() }
        }
    }

    fun replaceAll(items: List<DbFilm>): Completable {
        return module.dbConnector.modifyInTransaction(listOf(clear(), save(items)))
    }

    fun switchFavourite(item: Film): Completable {
        return module.dbConnector.modify {
            Completable.fromCallable {
                if (item.isFavourite) {
                    module.dbFavouriteFilmDao.delete(item.id)
                } else {
                    with(item) {
                        module.dbFavouriteFilmDao.insert(
                            DbFavouriteFilm(
                                id,
                                posterPath,
                                title,
                                overview,
                                releaseDate
                            )
                        )
                    }
                }
            }
        }
    }
}