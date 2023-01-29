package com.doskoch.template.anime.data

import com.doskoch.template.anime.domain.model.AnimeDetails
import com.doskoch.template.api.jikan.services.anime.responses.GetAnimeResponse
import javax.inject.Inject

class AnimeDataConverter @Inject constructor() {

    fun toAnimeDetails(data: GetAnimeResponse.Data) = with(data) {
        AnimeDetails(
            imageUrl = images.webp.imageUrl,
            approved = approved,
            score = score,
            scoredBy = scoredBy,
            rank = rank,
            popularity = popularity,
            genres = genres.map { it.name },
            description = synopsis,
            type = type,
            episodes = episodes,
            status = status,
            premiered = year.toString(),
            themes = themes.map { it.name },
            duration = duration,
            rating = rating,
            studios = studios.map { it.name }
        )
    }
}