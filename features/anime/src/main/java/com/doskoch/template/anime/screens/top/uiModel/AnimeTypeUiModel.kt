package com.doskoch.template.anime.screens.top.uiModel

import androidx.annotation.StringRes
import com.doskoch.template.anime.R
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType

enum class AnimeTypeUiModel(@StringRes val stringId: Int) {
    Tv(R.string.anime_type_tv),
    Movie(R.string.anime_type_movie),
    Ova(R.string.anime_type_ova),
    Special(R.string.anime_type_special),
    Ona(R.string.anime_type_ona),
    Music(R.string.anime_type_music)
}

fun AnimeTypeUiModel.toRemoteAnimeType() = when(this) {
    AnimeTypeUiModel.Tv -> RemoteAnimeType.Tv
    AnimeTypeUiModel.Movie -> RemoteAnimeType.Movie
    AnimeTypeUiModel.Ova -> RemoteAnimeType.Ova
    AnimeTypeUiModel.Special -> RemoteAnimeType.Special
    AnimeTypeUiModel.Ona -> RemoteAnimeType.Ona
    AnimeTypeUiModel.Music -> RemoteAnimeType.Music
}