package com.doskoch.movies.database.schema.films

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase
import com.doskoch.movies.database.BaseDao

@Dao
abstract class DbFilmDao(database: RoomDatabase) : BaseDao<DbFilm>(database) {

    @Query("select * from DbFilm")
    abstract fun select(): List<DbFilm>

    @Query("select count(rowId) from DbFilm")
    abstract fun count(): Int

    @Query("delete from DbFilm")
    abstract fun clear()

}