package com.doskoch.template.anime.domain

import com.doskoch.template.anime.domain.model.AnimeItem

interface AnimeInMemoryRepository {
    fun storeAnime(clearExistingData: Boolean, previousKey: Int?, currentKey: Int, nextKey: Int?, page: List<AnimeItem>)
    fun clearStorage()
    fun lastPagingKey(): Int?
    fun anime(id: Int): AnimeItem
}
