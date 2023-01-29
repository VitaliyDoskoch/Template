package com.doskoch.template.anime.domain.screens.details

import com.doskoch.template.anime.domain.AnimeLocalRepository
import javax.inject.Inject

class GetIsFavoriteAnimeUseCase @Inject constructor(private val repository: AnimeLocalRepository) {
    fun invoke(id: Int) = repository.isFavorite(id)
}
