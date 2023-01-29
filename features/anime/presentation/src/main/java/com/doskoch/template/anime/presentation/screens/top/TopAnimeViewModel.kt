package com.doskoch.template.anime.presentation.screens.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.presentation.common.uiModel.AnimeUiModel
import com.doskoch.template.anime.presentation.common.uiModel.toUiModel
import com.doskoch.template.anime.domain.common.DeleteAnimeFromFavoriteUseCase
import com.doskoch.template.anime.presentation.common.useCase.SaveAnimeToFavoriteUseCase
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.presentation.screens.top.uiModel.AnimeTypeUiModel
import com.doskoch.template.anime.presentation.screens.top.uiModel.toRemoteAnimeType
import com.doskoch.template.anime.presentation.screens.top.useCase.ClearAnimeStorageUseCase
import com.doskoch.template.anime.presentation.screens.top.useCase.GetFavoriteAnimeIdsUseCase
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.ErrorMapper
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import com.doskoch.template.core.android.ext.launchAction
import com.doskoch.template.core.domain.auth.useCase.LogoutUseCase
import com.doskoch.template.core.kotlin.di.FeatureScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TopAnimeViewModel @Inject constructor(
    @FeatureScoped
    private val navigator: AnimeFeatureNavigator,
    private val pagerFactory: PagerFactory,
    private val clearAnimeStorageUseCase: ClearAnimeStorageUseCase,
    private val getFavoriteAnimeIdsUseCase: GetFavoriteAnimeIdsUseCase,
    private val saveAnimeToFavoriteUseCase: SaveAnimeToFavoriteUseCase,
    private val deleteAnimeFromFavoriteUseCase: DeleteAnimeFromFavoriteUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    private val _state: MutableStateFlow<TopAnimeState> = MutableStateFlow(
        TopAnimeState(
            animeType = AnimeTypeUiModel.Tv,
            showAnimeTypeMenu = false,
            hasFavorite = false,
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
                onItemClick = this::onItemClick,
                onItemFavoriteClick = this::onItemFavoriteClick
            )
        )
    )
    val state = _state.asStateFlow()

    private val favoriteIdsFlow = getFavoriteAnimeIdsUseCase.invoke()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val pagingFlow = state
        .map { it.animeType }
        .distinctUntilChanged()
        .onEach { clearAnimeStorageUseCase.invoke() }
        .flatMapLatest { pagerFactory.create(it.toRemoteAnimeType()).flow }
        .cachedIn(viewModelScope)
        .combine(favoriteIdsFlow) { data, favoriteIds -> data.map { it.toUiModel(isFavorite = it.malId in favoriteIds) } }

    init {
        _state.update { it.copy(pagingFlow = pagingFlow) }
        observeHasFavorite()
    }

    private fun observeHasFavorite() = launchAction(
        action = {
            favoriteIdsFlow.collectLatest { ids -> _state.update { it.copy(hasFavorite = ids.isNotEmpty()) } }
        },
        onError = { globalErrorHandler.handle(errorMapper.toCoreError(it, CoreError.FailedToLoadData)) }
    )

    private fun onAnimeTypeClick() = _state.update { it.copy(showAnimeTypeMenu = true) }

    private fun onDismissAnimeTypeMenu() = _state.update { it.copy(showAnimeTypeMenu = false) }

    private fun onUpdateAnimeType(type: AnimeTypeUiModel) = _state.update { it.copy(animeType = type, showAnimeTypeMenu = false) }

    private fun onFavoriteClick() = navigator.toFavorite()

    private fun onLogoutClick() = _state.update { it.copy(showLogoutDialog = true) }

    private fun onDismissLogoutDialog() = _state.update { it.copy(showLogoutDialog = false) }

    private fun onConfirmLogoutClick() = launchAction(
        action = {
            logoutUseCase.invoke()
            navigator.toSplash()
        },
        onError = { globalErrorHandler.handle(errorMapper.toCoreError(it)) }
    )

    private fun onItemClick(item: AnimeUiModel) = navigator.toDetails(item.id, item.title)

    private fun onItemFavoriteClick(item: AnimeUiModel) = launchAction(
        action = {
            if (item.isFavorite) {
                deleteAnimeFromFavoriteUseCase.invoke(item.id)
            } else {
                saveAnimeToFavoriteUseCase.invoke(item.id)
            }
        },
        onError = { globalErrorHandler.handle(errorMapper.toCoreError(it, CoreError.FailedToSaveChanges)) }
    )

    fun interface PagerFactory {
        fun create(remoteAnimeType: RemoteAnimeType): Pager<Int, GetTopAnimeResponse.Data>
    }
}
