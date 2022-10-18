package com.doskoch.template.database.schema.anime

import androidx.room.Entity
import com.doskoch.template.database.common.BaseEntity

@Entity
data class DbAnime(
    val id: Int,
    val approved: Boolean,
    val imageUrl: String,
    val title: String,
    val genres: List<String>,
    val score: Float,
    val scoredBy: Int
) : BaseEntity()