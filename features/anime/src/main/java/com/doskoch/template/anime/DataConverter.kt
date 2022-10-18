package com.doskoch.template.anime

import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
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