package com.doskoch.movies.database.schema.films

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase
import com.doskoch.movies.database.BaseDao

@Dao
abstract class DbFavouriteFilmDao(database: RoomDatabase) : BaseDao<DbFavouriteFilm>(database) {

    @Query("select * from DbFavouriteFilm")
    abstract fun select(): List<DbFavouriteFilm>

    @Query("select count(rowId) from DbFavouriteFilm")
    abstract fun count(): Int

    @Query("delete from DbFavouriteFilm where id = :id")
    abstract fun delete(id: Long)
}