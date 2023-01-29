package com.doskoch.template.anime.domain.common

import com.doskoch.template.anime.domain.AnimeInMemoryRepository
import com.doskoch.template.anime.domain.AnimeLocalRepository
import javax.inject.Inject

class SaveAnimeToFavoriteUseCase @Inject constructor(
    private val inMemoryRepository: AnimeInMemoryRepository,
    private val localRepository: AnimeLocalRepository
) {
    suspend fun invoke(id: Int) {
        val item = inMemoryRepository.anime(id)
        localRepository.save(item)
    }
}
