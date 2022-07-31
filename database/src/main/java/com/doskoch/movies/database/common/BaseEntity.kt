package com.doskoch.movies.database.common

import androidx.room.PrimaryKey

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var rowId: Long = 0
        protected set
}
