package com.doskoch.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doskoch.movies.database.config.DATABASE_NAME
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilmDao
import com.doskoch.movies.database.modules.films.entity.DbFilm
import com.doskoch.movies.database.modules.films.entity.DbFilmDao
import com.doskoch.movies.database.modules.films.view.FilmDao

@Database(
    entities = [
        DbFilm::class,
        DbFavouriteFilm::class
    ],
    version = 1
)
@TypeConverters(
    CollectionsConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun dbFilmDao(): DbFilmDao
    abstract fun dbFavouriteFilmDao(): DbFavouriteFilmDao

    abstract fun filmDao(): FilmDao

}