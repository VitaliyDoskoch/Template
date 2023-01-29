package com.doskoch.template.anime.presentation.screens.top.useCase

import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.paging.SimpleInMemoryStorage
import com.doskoch.template.core.kotlin.di.FeatureScoped
import javax.inject.Inject

class StoreAnimeUseCase @Inject constructor(
    @FeatureScoped
    private val storage: SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>
) {
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
