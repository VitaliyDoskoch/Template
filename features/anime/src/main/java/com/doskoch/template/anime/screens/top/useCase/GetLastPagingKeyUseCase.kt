package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage
import javax.inject.Inject

class GetLastPagingKeyUseCase @Inject constructor(private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>) {
    fun invoke() = storage.pages.value.keys.lastOrNull()
}
