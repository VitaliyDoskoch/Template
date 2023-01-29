package com.doskoch.template.anime.presentation.screens.details

import com.doskoch.template.anime.domain.model.AnimeDetails
import com.doskoch.template.core.android.components.error.CoreError

data class AnimeDetailsState(
    val title: String,
    val isFavorite: Boolean,
    val screenState: ScreenState,
    val actions: Actions
) {
    sealed class ScreenState {
        object Loading : ScreenState()

        data class Content(
            val data: AnimeDetails
        ) : ScreenState()

        data class Error(val error: CoreError) : ScreenState()
    }

    data class Actions(
        val onBackClick: () -> Unit,
        val onFavoriteClick: () -> Unit
    )
}
