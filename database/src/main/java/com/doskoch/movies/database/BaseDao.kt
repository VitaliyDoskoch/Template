package com.doskoch.movies.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.RoomDatabase
import androidx.room.Update

@Dao
abstract class BaseDao<T : Any>(val database: RoomDatabase) {

    /**
     * @return rowId of inserted item.
     */
    @Insert(onConflict = REPLACE)
    abstract fun insert(item: T): Long

    /**
     * @return list of rowIds of inserted items.
     */
    @Insert(onConflict = REPLACE)
    abstract fun insert(items: List<T>): List<Long>

    /**
     * @return number of updated rows.
     */
    @Update(onConflict = REPLACE)
    abstract fun update(item: T): Int

    /**
     * @return number of updated rows.
     */
    @Update(onConflict = REPLACE)
    abstract fun update(items: List<T>): Int

    /**
     * @return number of deleted rows.
     */
    @Delete
    abstract fun delete(item: T): Int

    /**
     * @return number of deleted rows.
     */
    @Delete
    abstract fun delete(items: List<T>): Int
}
