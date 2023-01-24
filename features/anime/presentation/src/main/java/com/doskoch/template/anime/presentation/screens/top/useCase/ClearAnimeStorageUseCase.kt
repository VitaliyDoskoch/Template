package com.doskoch.template.anime.presentation.screens.top.useCase

import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import javax.inject.Inject

class ClearAnimeStorageUseCase @Inject constructor(private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>) {
    fun invoke() = storage.inTransaction { clear() }
}
