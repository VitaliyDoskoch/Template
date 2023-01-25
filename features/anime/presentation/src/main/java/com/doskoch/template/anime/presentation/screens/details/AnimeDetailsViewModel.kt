package com.doskoch.template.anime.presentation.screens.details

import androidx.lifecycle.ViewModel
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.anime.presentation.screens.details.AnimeDetailsState.ScreenState
import com.doskoch.template.anime.presentation.screens.details.useCase.GetIsFavoriteAnimeUseCase
import com.doskoch.template.anime.presentation.screens.details.useCase.LoadAnimeDetailsUseCase
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.ErrorMapper
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import com.doskoch.template.core.android.ext.launchAction
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class AnimeDetailsViewModel @AssistedInject constructor(
    @Assisted private val animeId: Int,
    @Assisted private val title: String,
    private val navigator: AnimeFeatureNavigator,
    private val globalErrorHandler: GlobalErrorHandler,
    private val errorMapper: ErrorMapper,
    private val getIsFavoriteAnimeUseCase: GetIsFavoriteAnimeUseCase,
    private val loadAnimeDetailsUseCase: LoadAnimeDetailsUseCase,
    private val saveAnimeToFavoriteUseCase: com.doskoch.template.anime.presentation.common.useCase.SaveAnimeToFavoriteUseCase,
    private val deleteAnimeFromFavoriteUseCase: com.doskoch.template.anime.presentation.common.useCase.DeleteAnimeFromFavoriteUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AnimeDetailsState> = MutableStateFlow(
        AnimeDetailsState(
            title = title,
            isFavorite = false,
            screenState = ScreenState.Loading,
            actions = AnimeDetailsState.Actions(
                onBackClick = this::onBackClick,
                onFavoriteClick = this::onFavoriteClick
            )
        )
    )
    val state = _state.asStateFlow()

    init {
        observeIsFavorite()
        loadAnimeDetails()
    }

    private fun observeIsFavorite() = launchAction(
        action = {
            getIsFavoriteAnimeUseCase.invoke(animeId).collectLatest { isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }
        },
        onError = { error ->
            _state.update { it.copy(screenState = ScreenState.Error(errorMapper.toCoreError(error, CoreError.FailedToLoadData))) }
        }
    )

    private fun loadAnimeDetails() = launchAction(
        action = {
            _state.update { it.copy(screenState = ScreenState.Loading) }
            _state.update { it.copy(screenState = loadAnimeDetailsUseCase.invoke(animeId).data.toContent()) }
        },
        onError = { error ->
            _state.update { it.copy(screenState = ScreenState.Error(errorMapper.toCoreError(error, CoreError.FailedToLoadData))) }
        }
    )

    private fun onBackClick() = navigator.navigateUp()

    private fun onFavoriteClick() = launchAction(
        action = {
            if (state.value.isFavorite) {
                deleteAnimeFromFavoriteUseCase.invoke(animeId)
            } else {
                saveAnimeToFavoriteUseCase.invoke(animeId)
            }
        },
        onError = { globalErrorHandler.handle(errorMapper.toCoreError(it, CoreError.FailedToSaveChanges)) }
    )

    @AssistedFactory
    interface Factory {
        fun create(animeId: Int, title: String): AnimeDetailsViewModel
    }
}
