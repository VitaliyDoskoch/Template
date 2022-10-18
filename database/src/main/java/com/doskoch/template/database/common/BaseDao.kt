package com.doskoch.template.database.common

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.RoomDatabase
import androidx.room.Update

@Dao
abstract class BaseDao<T : Any>(val database: RoomDatabase) {

    /**
     * @return rowId of the inserted item.
     */
    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(item: T): Long

    /**
     * @return list of rowIds of the inserted items.
     */
    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(items: List<T>): List<Long>

    /**
     * @return the number of the updated rows.
     */
    @Update(onConflict = REPLACE)
    abstract suspend fun update(item: T): Int

    /**
     * @return the number of the updated rows.
     */
    @Update(onConflict = REPLACE)
    abstract suspend fun update(items: List<T>): Int

    /**
     * @return the number of the deleted rows.
     */
    @Delete
    abstract suspend fun delete(item: T): Int

    /**
     * @return the number of the deleted rows.
     */
    @Delete
    abstract suspend fun delete(items: List<T>): Int
}
