package com.doskoch.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doskoch.movies.database.common.CollectionsConverter
import com.doskoch.movies.database.schema.DbEntity

@Database(
    entities = [
        DbEntity::class
    ],
    version = 1
)
@TypeConverters(CollectionsConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
