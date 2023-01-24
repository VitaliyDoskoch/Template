package com.doskoch.template.anime.presentation.common

import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.database.schema.anime.DbAnime

fun GetTopAnimeResponse.Data.toDbAnime() = DbAnime(
    id = malId,
    approved = approved,
    imageUrl = images.webp.imageUrl,
    title = title,
    genres = genres.map { it.name },
    score = score,
    scoredBy = scoredBy
)
