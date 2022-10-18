package com.doskoch.template.database.common

import androidx.room.PrimaryKey

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var rowId: Long = 0
        internal set
}
