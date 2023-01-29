package com.doskoch.template.anime.data

import com.doskoch.template.anime.domain.model.AnimeDetails
import com.doskoch.template.anime.domain.model.AnimeItem
import com.doskoch.template.anime.domain.model.AnimeType
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.anime.responses.GetAnimeResponse
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.database.schema.anime.DbAnime
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

    fun toAnimeItem(data: GetTopAnimeResponse.Data) = with(data) {
        AnimeItem(
            id = malId,
            approved = approved,
            imageUrl = images.webp.imageUrl,
            title = title,
            genres = genres.map { it.name },
            score = score,
            scoredBy = scoredBy
        )
    }

    fun toDbAnime(data: AnimeItem) = with(data) {
        DbAnime(
            id = id,
            approved = approved,
            imageUrl = imageUrl,
            title = title,
            genres = genres,
            score = score,
            scoredBy = scoredBy
        )
    }

    fun toAnimeItem(data: DbAnime) = with(data) {
        AnimeItem(
            id = id,
            approved = approved,
            imageUrl = imageUrl,
            title = title,
            genres = genres,
            score = score,
            scoredBy = scoredBy
        )
    }

    fun toRemoteAnimeType(data: AnimeType) = when (data) {
        AnimeType.Tv -> RemoteAnimeType.Tv
        AnimeType.Movie -> RemoteAnimeType.Movie
        AnimeType.Ova -> RemoteAnimeType.Ova
        AnimeType.Special -> RemoteAnimeType.Special
        AnimeType.Ona -> RemoteAnimeType.Ona
        AnimeType.Music -> RemoteAnimeType.Music
    }
}
