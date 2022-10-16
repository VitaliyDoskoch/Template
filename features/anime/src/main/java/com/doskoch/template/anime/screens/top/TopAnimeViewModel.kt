package com.doskoch.template.anime.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.screens.top.uiModel.AnimeTypeUiModel
import com.doskoch.template.anime.screens.top.uiModel.toRemoteAnimeType
import com.doskoch.template.anime.screens.top.useCase.ClearAnimeUseCase
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopAnimeViewModel(
    private val navigator: AnimeFeatureNavigator,
    private val pagerFactory: PagerFactory,
    private val clearAnimeUseCase: ClearAnimeUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val globalErrorHandler: GlobalErrorHandler
) : ViewModel() {

    private val _state: MutableStateFlow<TopAnimeState> = MutableStateFlow(
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
        .map { it.animeType }
        .distinctUntilChanged()
        .flatMapLatest { pagerFactory.create(it.toRemoteAnimeType()).flow }
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

    private fun onUpdateAnimeType(type: AnimeTypeUiModel) = launchAction(
        action = {
            clearAnimeUseCase.invoke()
            _state.update { it.copy(animeType = type, showAnimeTypeMenu = false) }
        },
        onError = { globalErrorHandler.handle(it.toCoreError()) }
    )

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