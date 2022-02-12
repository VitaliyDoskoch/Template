package com.doskoch.movies.database

import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlin.reflect.KClass

/**
 * Lets to observe [InvalidationTracker], using [KClass]es of declared entities.
 */
fun <E : Any> RoomDatabase.observeChanges(entities: Set<KClass<out E>>): Flowable<Set<KClass<out E>>> {
    return observeInvalidationTracker(entities.map { it.java.simpleName }.toSet())
        .map { tables ->
            entities.filter { tables.contains(it.java.simpleName) }.toSet()
        }
}

/**
 * Lets to observe [InvalidationTracker] in reactive manner.
 */
fun RoomDatabase.observeInvalidationTracker(tableNames: Set<String>): Flowable<Set<String>> {
    return Flowable.create({ emitter ->
        try {
            val observer = object : InvalidationTracker.Observer(tableNames.toTypedArray()) {
                override fun onInvalidated(tables: MutableSet<String>) {
                    if (!emitter.isCancelled) {
                        emitter.onNext(tables)
                    }
                }
            }

            emitter.setCancellable { invalidationTracker.removeObserver(observer) }

            invalidationTracker.addObserver(observer)
        } catch (throwable: Throwable) {
            if (!emitter.isCancelled) {
                emitter.onError(throwable)
            }
        }
    }, BackpressureStrategy.LATEST)
}

/**
 * Clears all tables and resets autoincrement counters,
 */
fun RoomDatabase.clear(): Completable {
    return Completable.fromCallable {
        clearAllTables()
        query(SimpleSQLiteQuery("delete from sqlite_sequence"))
    }
}