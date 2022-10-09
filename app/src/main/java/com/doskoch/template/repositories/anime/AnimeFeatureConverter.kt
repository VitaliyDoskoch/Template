package com.doskoch.template.repositories.anime

import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.api.jikan.common.enum.AnimeType as RemoteAnimeType
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse

class AnimeFeatureConverter {

    fun toRemoteType(type: AnimeType) = when(type) {
        AnimeType.Tv -> RemoteAnimeType.Tv
        AnimeType.Movie -> RemoteAnimeType.Movie
        AnimeType.Ova -> RemoteAnimeType.Ova
        AnimeType.Special -> RemoteAnimeType.Special
        AnimeType.Ona -> RemoteAnimeType.Ona
        AnimeType.Music -> RemoteAnimeType.Music
    }

    fun toAnimeItem(data: GetTopAnimeResponse.Data) = with(data) {
        AnimeItem(
            id = malId,
            approved = approved,
            imageUrl = images.webp.imageUrl,
            title = title,
            genres = genres.map { it.name },
            score = score,
            scoredBy = scoredBy,
            isFavorite = false
        )
    }
}