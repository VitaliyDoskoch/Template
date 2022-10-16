package com.doskoch.template.anime.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.screens.top.uiModel.AnimeTypeUiModel
import com.doskoch.template.anime.screens.top.uiModel.toRemoteAnimeType
import com.doskoch.template.anime.uiModel.AnimeUiModel
import com.doskoch.template.anime.uiModel.toUiModel
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.functions.launchAction
import com.doskoch.template.core.useCase.authorization.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopAnimeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val pagerFactory: PagerFactory,
    private val globalErrorHandler: GlobalErrorHandler,
    private val navigator: AnimeFeatureNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(
        TopAnimeState(
            animeType = AnimeTypeUiModel.Tv,
            showAnimeTypeMenu = false,
            showLogoutDialog = false,
            pagingFlow = emptyFlow(),
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
    )
    val state = _state.asStateFlow()

    private val pagingFlow = state
        .flatMapLatest { pagerFactory.create(it.animeType.toRemoteAnimeType()).flow }
        .map { it.map(GetTopAnimeResponse.Data::toUiModel) }
        .cachedIn(viewModelScope)

    init {
        _state.update { it.copy(pagingFlow = pagingFlow) }
    }

    private fun onAnimeTypeClick() {
        _state.update { it.copy(showAnimeTypeMenu = true) }
    }

    private fun onDismissAnimeTypeMenu() {
        _state.update { it.copy(showAnimeTypeMenu = false) }
    }

    private fun onUpdateAnimeType(type: AnimeTypeUiModel) {
//        storage.clear()//TODO

        _state.update { it.copy(animeType = type, showAnimeTypeMenu = false) }
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

    private fun onConfirmLogoutClick() = launchAction(
        action = {
            logoutUseCase.invoke()
            navigator.toSplash()
        },
        onError = { globalErrorHandler.handle(it.toCoreError()) }
    )

    private fun onItemClick(item: AnimeUiModel) = viewModelScope.launch { navigator.toDetails(item.id) }

    fun interface PagerFactory {
        fun create(remoteAnimeType: RemoteAnimeType): Pager<Int, GetTopAnimeResponse.Data>
    }
}