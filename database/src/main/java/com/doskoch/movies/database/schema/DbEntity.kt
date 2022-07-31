package com.doskoch.movies.database.schema

import androidx.room.Entity
import com.doskoch.movies.database.common.BaseEntity

@Entity
data class DbEntity(val id: String) : BaseEntity()
