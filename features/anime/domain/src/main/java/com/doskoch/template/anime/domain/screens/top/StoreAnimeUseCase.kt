package com.doskoch.template.anime.domain.screens.top

import com.doskoch.template.anime.domain.AnimeInMemoryRepository
import com.doskoch.template.anime.domain.model.AnimeItem
import javax.inject.Inject

class StoreAnimeUseCase @Inject constructor(
    private val repository: AnimeInMemoryRepository
) {
    fun invoke(clearExistingData: Boolean, previousKey: Int?, currentKey: Int, nextKey: Int?, page: List<AnimeItem>) =
        repository.storeAnime(clearExistingData, previousKey, currentKey, nextKey, page)
}
