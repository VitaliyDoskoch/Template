package com.doskoch.template.anime.presentation.screens.top

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.doskoch.template.anime.presentation.R
import com.doskoch.template.anime.presentation.common.ui.AnimeItem
import com.doskoch.template.anime.presentation.screens.top.uiModel.AnimeTypeUiModel
import com.doskoch.template.core.android.ui.common.dialogs.LogoutDialog
import com.doskoch.template.core.android.ui.modifiers.simpleVerticalScrollbar
import com.doskoch.template.core.android.ui.paging.LoadStateItem
import com.doskoch.template.core.android.ui.paging.PagingScaffold
import com.doskoch.template.core.android.ui.paging.PagingSwipeRefresh
import com.doskoch.template.core.android.ui.paging.retryWhenNetworkAvailable
import com.doskoch.template.core.android.ui.theme.Dimensions
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TopAnimeScreen(vm: TopAnimeViewModel = hiltViewModel()) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        val items = state.pagingFlow.collectAsLazyPagingItems().retryWhenNetworkAvailable()

        PagingSwipeRefresh(
            loadState = items.loadState.refresh,
            onRefresh = items::refresh,
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
        ) { swipeRefreshState ->
            PagingScaffold(
                itemCount = items.itemCount,
                loadState = items.loadState.takeIf { !swipeRefreshState.isRefreshing },
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val lazyListState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .simpleVerticalScrollbar(lazyListState),
                    state = lazyListState
                ) {
                    itemsIndexed(
                        items = items,
                        key = { _, item -> item.id }
                    ) { position, item ->
                        item?.let {
                            AnimeItem(
                                item = item,
                                position = position,
                                onFavoriteClick = { state.actions.onItemFavoriteClick(item) },
                                modifier = Modifier
                                    .clickable { state.actions.onItemClick(item) }
                            )
                        }
                    }

                    LoadStateItem(itemCount = items.itemCount, loadState = items.loadState.append, onRetry = items::retry)
                }
            }
        }
    }

    if (state.showLogoutDialog) {
        LogoutDialog(
            onDismiss = state.actions.onDismissLogoutDialog,
            onConfirm = state.actions.onConfirmLogoutClick
        )
    }
}

@Composable
private fun TopBar(state: TopAnimeState) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colors.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
    }

    TopAppBar(
        modifier = Modifier
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = state.actions.onAnimeTypeClick)
                .padding(horizontal = Dimensions.space_16, vertical = Dimensions.space_8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(state.animeType.stringId),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )

            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimensions.icon_24),
                tint = MaterialTheme.colors.onPrimary
            )

            DropdownMenu(
                expanded = state.showAnimeTypeMenu,
                onDismissRequest = state.actions.onDismissAnimeTypeMenu,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.secondary),
                content = { AnimeTypeItems(state = state) }
            )
        }

        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = state.actions.onFavoriteClick
            ) {
                Icon(
                    painter = painterResource(if (state.hasFavorite) R.drawable.ic_star_filled else R.drawable.ic_start_outline),
                    contentDescription = stringResource(R.string.anime_feature_desc_to_favorite),
                    modifier = Modifier
                        .size(Dimensions.icon_24),
                    tint = MaterialTheme.colors.secondary
                )
            }

            IconButton(
                onClick = state.actions.onLogoutClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = stringResource(R.string.anime_feature_desc_logout),
                    modifier = Modifier
                        .size(Dimensions.icon_24),
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.AnimeTypeItems(state: TopAnimeState) {
    AnimeTypeUiModel.values().forEach {
        key(it) {
            DropdownMenuItem(
                onClick = { state.actions.onUpdateAnimeType(it) }
            ) {
                Text(
                    text = stringResource(it.stringId),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}
