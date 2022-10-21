package com.doskoch.template.database.schema.anime

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase
import com.doskoch.template.database.common.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DbAnimeDao(database: RoomDatabase) : BaseDao<DbAnime>(database) {

    @Query("select * from DbAnime")
    abstract fun pagingSource(): PagingSource<Int, DbAnime>

    @Query("select DbAnime.id from DbAnime")
    abstract fun ids(): Flow<List<Int>>

    @Query("delete from DbAnime where DbAnime.id = :id")
    abstract suspend fun delete(id: Int)

    @Query("select exists(select DbAnime.id from DbAnime where DbAnime.id = :id)")
    abstract fun isFavorite(id: Int): Flow<Boolean>

}