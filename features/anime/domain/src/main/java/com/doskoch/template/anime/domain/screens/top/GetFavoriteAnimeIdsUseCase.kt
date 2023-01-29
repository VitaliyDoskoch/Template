package com.doskoch.template.anime.domain.screens.top

import com.doskoch.template.anime.domain.AnimeLocalRepository
import javax.inject.Inject

class GetFavoriteAnimeIdsUseCase @Inject constructor(private val repository: AnimeLocalRepository) {
    fun invoke() = repository.animeIds()
}
