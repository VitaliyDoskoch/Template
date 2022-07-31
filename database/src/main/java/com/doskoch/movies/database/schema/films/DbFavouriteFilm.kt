package com.doskoch.movies.database.schema.films

import androidx.room.Entity
import androidx.room.Index
import com.doskoch.movies.database.BaseEntity

@Entity(
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class DbFavouriteFilm(
    var id: Long,
    var posterPath: String?,
    var title: String,
    var overview: String,
    var releaseDate: Long
) : BaseEntity()
