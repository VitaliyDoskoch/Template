package com.doskoch.movies.core.components.database

import com.doskoch.movies.core.components.exceptions.ExpectedException
import com.doskoch.movies.core.components.exceptions.ExpectedException.ErrorCode.FAILED_TO_LOAD_DATA
import com.doskoch.movies.core.components.exceptions.ExpectedException.ErrorCode.FAILED_TO_SAVE_CHANGES
import com.doskoch.movies.database.AppDatabase
import com.doskoch.movies.database.BaseEntity
import com.doskoch.movies.database.observeChanges
//import io.reactivex.Completable
//import io.reactivex.Flowable
//import io.reactivex.Single
import kotlin.reflect.KClass

class DatabaseConnector(private val database: AppDatabase) {

//    fun observeChanges(entities: Set<KClass<out BaseEntity>>,
//                       startWith: Boolean): Flowable<Set<KClass<out BaseEntity>>> {
//        return database.observeChanges(entities)
//            .mergeWith(if (startWith) Flowable.just(entities) else Flowable.empty())
//            .filter { entities.intersect(it).isNotEmpty() }
//            .onErrorResumeNext { error: Throwable ->
//                Flowable.error(ExpectedException(FAILED_TO_LOAD_DATA, error))
//            }
//    }

//    fun <T> observeChanges(entities: Set<KClass<out BaseEntity>>,
//                           startWith: Boolean,
//                           mapper: AppDatabase.(entities: Set<KClass<out BaseEntity>>) -> Flowable<T>): Flowable<T> {
//        return observeChanges(entities, startWith)
//            .flatMap {
//                mapper(database, it)
//                    .onErrorResumeNext { error: Throwable ->
//                        Flowable.error(ExpectedException(FAILED_TO_LOAD_DATA, error))
//                    }
//            }
//    }

//    fun <T> select(getter: AppDatabase.() -> Single<T>): Single<T> {
//        return getter.invoke(database)
//            .onErrorResumeNext { error ->
//                Single.error(ExpectedException(FAILED_TO_LOAD_DATA, error))
//            }
//    }

//    fun modify(modifier: AppDatabase.() -> Completable): Completable {
//        return modifier.invoke(database)
//            .onErrorResumeNext { throwable ->
//                Completable.error(ExpectedException(FAILED_TO_SAVE_CHANGES, throwable))
//            }
//    }

//    fun modifyInTransaction(modifiers: List<Completable>): Completable {
//        return Completable.fromCallable {
//            database.runInTransaction {
//                modifiers.forEach(Completable::blockingAwait)
//            }
//        }
//            .onErrorResumeNext { throwable ->
//                Completable.error(ExpectedException(FAILED_TO_SAVE_CHANGES, throwable))
//            }
//    }
}