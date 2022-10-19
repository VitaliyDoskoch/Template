package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage

class GetLastPagingKeyUseCase(private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>) {
    fun invoke() = storage.pages.value.keys.lastOrNull()
}