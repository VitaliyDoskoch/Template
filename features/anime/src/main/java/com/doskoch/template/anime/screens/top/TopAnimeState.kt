package com.doskoch.template.anime.screens.top

import androidx.paging.PagingData
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import kotlinx.coroutines.flow.Flow

data class TopAnimeState(
    val animeType: AnimeType,
    val showAnimeTypeMenu: Boolean,
    val showLogoutDialog: Boolean,
    val pagingData: Flow<PagingData<AnimeItem>>,
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
        val onItemClick: (AnimeItem) -> Unit
    )
}