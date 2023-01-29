package com.doskoch.template.anime.domain.screens.top

import com.doskoch.template.anime.domain.AnimeInMemoryRepository
import javax.inject.Inject

class ClearAnimeStorageUseCase @Inject constructor(private val repository: AnimeInMemoryRepository) {
    fun invoke() = repository.clearStorage()
}
