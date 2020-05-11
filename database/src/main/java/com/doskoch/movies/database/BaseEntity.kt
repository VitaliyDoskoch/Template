package com.doskoch.movies.database

import androidx.room.PrimaryKey

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var rowId: Long = 0
}