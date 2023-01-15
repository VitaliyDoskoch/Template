package com.doskoch.template.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doskoch.template.database.common.CollectionsConverter
import com.doskoch.template.database.schema.anime.DbAnime
import com.doskoch.template.database.schema.anime.DbAnimeDao

@Database(
    entities = [
        DbAnime::class
    ],
    version = 1
)
@TypeConverters(CollectionsConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(application: Application): AppDatabase {
            return Room
                .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun dbAnimeDao(): DbAnimeDao
}
