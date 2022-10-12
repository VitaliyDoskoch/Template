package com.doskoch.template.anime.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.di.LogoutUseCase
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.functions.perform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopAnimeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val pagerFactory: PagerFactory,
    private val globalErrorHandler: GlobalErrorHandler,
    private val navigator: AnimeFeatureNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state = _state.asStateFlow()

    private fun initialState(): TopAnimeState = TopAnimeState(
        animeType = AnimeType.Tv,
        showAnimeTypeMenu = false,
        showLogoutDialog = false,
        pagingData = emptyFlow(),// pagerFactory.create(AnimeType.Tv).flow.cachedIn(viewModelScope),
        actions = TopAnimeState.Actions(
            onAnimeTypeClick = this::onAnimeTypeClick,
            onDismissAnimeTypeMenu = this::onDismissAnimeTypeMenu,
            onUpdateAnimeType = this::onUpdateAnimeType,
            onFavoriteClick = this::onFavoriteClick,
            onLogoutClick = this::onLogoutClick,
            onDismissLogoutDialog = this::onDismissLogoutDialog,
            onConfirmLogoutClick = this::onConfirmLogoutClick,
            onItemClick = this::onItemClick
        )
    )

    private fun onAnimeTypeClick() {
        _state.update { it.copy(showAnimeTypeMenu = true) }
    }

    private fun onDismissAnimeTypeMenu() {
        _state.update { it.copy(showAnimeTypeMenu = false) }
    }

    private fun onUpdateAnimeType(type: AnimeType) {
//        storage.clear()//TODO

        _state.update {
            it.copy(
                animeType = type,
                showAnimeTypeMenu = false,
                pagingData = pagerFactory.create(type).flow.cachedIn(viewModelScope)
            )
        }
    }

    private fun onFavoriteClick() {
        navigator.toFavorite()
    }

    private fun onLogoutClick() {
        _state.update { it.copy(showLogoutDialog = true) }
    }

    private fun onDismissLogoutDialog() {
        _state.update { it.copy(showLogoutDialog = false) }
    }

    private fun onConfirmLogoutClick() = perform(
        action = {
            logoutUseCase.invoke()
            navigator.toSplash()
        },
        onError = { globalErrorHandler.handle(it.toCoreError()) }
    )

    private fun onItemClick(item: AnimeItem) = viewModelScope.launch { navigator.toDetails(item.id) }

    fun interface PagerFactory {
        fun create(animeType: AnimeType): Pager<Int, AnimeItem>
    }
}