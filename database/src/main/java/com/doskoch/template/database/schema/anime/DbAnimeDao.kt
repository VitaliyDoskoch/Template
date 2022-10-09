package com.doskoch.template.database.schema.anime

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase
import com.doskoch.template.database.common.BaseDao

@Dao
abstract class DbAnimeDao(database: RoomDatabase) : BaseDao<DbAnime>(database) {

    @Query("SELECT * FROM DbAnime")
    abstract fun pagingSource(): PagingSource<Int, DbAnime>
}