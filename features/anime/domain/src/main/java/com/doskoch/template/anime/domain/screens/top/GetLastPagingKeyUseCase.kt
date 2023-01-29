package com.doskoch.template.anime.domain.screens.top

import com.doskoch.template.anime.domain.AnimeInMemoryRepository
import javax.inject.Inject

class GetLastPagingKeyUseCase @Inject constructor(private val repository: AnimeInMemoryRepository) {
    fun invoke() = repository.lastPagingKey()
}
