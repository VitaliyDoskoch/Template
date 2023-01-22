package com.doskoch.template.anime.screens.top.useCase

import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import javax.inject.Inject

class StoreAnimeUseCase @Inject constructor(private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>) {
    fun invoke(
        clearExistingData: Boolean,
        previousKey: Int?,
        currentKey: Int,
        nextKey: Int?,
        page: List<GetTopAnimeResponse.Data>
    ) = storage.inTransaction {
        if (clearExistingData) {
            clear()
        }

        store(previousKey, currentKey, nextKey, page)
    }
}
