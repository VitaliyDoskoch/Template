package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage

class ClearAnimeUseCase(private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>) {
    fun invoke() = storage.inTransaction { update(emptyMap()) }
}