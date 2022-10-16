package com.doskoch.template.anime.screens.top

import androidx.paging.PagingData
import com.doskoch.template.anime.uiModel.AnimeUiModel
import com.doskoch.template.api.jikan.common.enum.AnimeType
import kotlinx.coroutines.flow.Flow

data class TopAnimeState(
    val animeType: AnimeType,
    val showAnimeTypeMenu: Boolean,
    val showLogoutDialog: Boolean,
    val pagingDataFlow: Flow<PagingData<AnimeUiModel>>,
    val actions: Actions
) {
    data class Actions(
        val onAnimeTypeClick: () -> Unit,
        val onDismissAnimeTypeMenu: () -> Unit,
        val onUpdateAnimeType: (AnimeType) -> Unit,
        val onFavoriteClick: () -> Unit,
        val onLogoutClick: () -> Unit,
        val onDismissLogoutDialog: () -> Unit,
        val onConfirmLogoutClick: () -> Unit,
        val onItemClick: (AnimeUiModel) -> Unit
    )
}