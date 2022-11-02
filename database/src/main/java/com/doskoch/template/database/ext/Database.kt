package com.doskoch.template.database.ext

import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery

fun RoomDatabase.resetAutoIncrement() {
    query(SimpleSQLiteQuery("delete from sqlite_sequence"))
}
