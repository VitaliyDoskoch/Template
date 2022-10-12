package com.doskoch.template.anime.screens.favorite

import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.database.schema.anime.DbAnime

class Converter {

    fun toAnimeItem(data: DbAnime) = with(data) {
        AnimeItem(id, approved, imageUrl, title, genres, score, scoredBy, isFavorite)
    }

    fun toDbAnime(data: AnimeItem) = with(data) {
        DbAnime(id, approved, imageUrl, title, genres, score, scoredBy, isFavorite)
    }
}