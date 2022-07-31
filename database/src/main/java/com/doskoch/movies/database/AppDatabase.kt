package com.doskoch.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doskoch.movies.database.schema.films.DbFavouriteFilm
import com.doskoch.movies.database.schema.films.DbFavouriteFilmDao
import com.doskoch.movies.database.schema.films.DbFilm
import com.doskoch.movies.database.schema.films.DbFilmDao
import com.doskoch.movies.database.schema.films.FilmDao

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
