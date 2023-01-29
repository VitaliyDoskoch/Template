package com.doskoch.template.anime.domain.common

import com.doskoch.template.anime.domain.AnimeLocalRepository
import javax.inject.Inject

class DeleteAnimeFromFavoriteUseCase @Inject constructor(private val repository: AnimeLocalRepository) {
    suspend fun invoke(id: Int) = repository.deleteAnime(id)
}
