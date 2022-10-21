package com.doskoch.template.database.schema.anime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbAnime(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val approved: Boolean,
    val imageUrl: String,
    val title: String,
    val genres: List<String>,
    val score: Float,
    val scoredBy: Int
)
