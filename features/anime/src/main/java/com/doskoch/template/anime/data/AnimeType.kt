package com.doskoch.template.anime.data

import androidx.annotation.StringRes
import com.doskoch.template.anime.R
import com.doskoch.template.api.jikan.common.enum.AnimeType

val AnimeType.stringId: Int
    @StringRes
    get() = when(this) {
        AnimeType.Tv -> R.string.anime_type_tv
        AnimeType.Movie -> R.string.anime_type_movie
        AnimeType.Ova -> R.string.anime_type_ova
        AnimeType.Special -> R.string.anime_type_special
        AnimeType.Ona -> R.string.anime_type_ona
        AnimeType.Music -> R.string.anime_type_music
    }