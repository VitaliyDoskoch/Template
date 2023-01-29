package com.doskoch.template.anime.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.doskoch.template.anime.domain.common.DeleteAnimeFromFavoriteUseCase
import com.doskoch.template.anime.domain.model.AnimeItem
import com.doskoch.template.anime.presentation.common.uiModel.AnimeUiModel
import com.doskoch.template.anime.presentation.common.uiModel.toUiModel
import com.doskoch.template.anime.presentation.navigation.AnimeFeatureNavigator
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.ErrorMapper
import com.doskoch.template.core.android.components.error.GlobalErrorHandler
import com.doskoch.template.core.android.ext.launchAction
import com.doskoch.template.core.kotlin.di.FeatureScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoriteAnimeViewModel @Inject constructor(
    @FeatureScoped
    private val navigator: AnimeFeatureNavigator,
    private val flow: Flow<PagingData<AnimeItem>>,
    private val deleteAnimeFromFavoriteUseCase: DeleteAnimeFromFavoriteUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    private val _state = MutableStateFlow(
        FavoriteAnimeState(
            pagingFlow = emptyFlow(),
            actions = FavoriteAnimeState.Actions(
                onBackClick = this::onBackClick,
                onItemClick = this::onItemClick,
                onItemFavoriteClick = this::onItemFavoriteClick
            )
        )
    )
    val state = _state.asStateFlow()

    private val pagingFlow = flow
        .map { it.map { it.toUiModel(isFavorite = true) } }
        .cachedIn(viewModelScope)

    init {
        _state.update { it.copy(pagingFlow = pagingFlow) }
    }

    private fun onBackClick() = navigator.navigateUp()

    private fun onItemClick(item: AnimeUiModel) = navigator.toDetails(item.id, item.title)

    private fun onItemFavoriteClick(item: AnimeUiModel) = launchAction(
        action = { deleteAnimeFromFavoriteUseCase.invoke(item.id) },
        onError = { globalErrorHandler.handle(errorMapper.toCoreError(it, CoreError.FailedToSaveChanges)) }
    )
}
