package com.doskoch.template.anime.screens.top

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.doskoch.template.anime.R
import com.doskoch.template.anime.di.Module
import com.doskoch.template.anime.screens.top.uiModel.AnimeTypeUiModel
import com.doskoch.template.anime.ui.AnimeItem
import com.doskoch.template.api.jikan.common.enum.RemoteAnimeType
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.core.ui.ErrorItem
import com.doskoch.template.core.ui.LoadingItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TopAnimeScreen(vm: TopAnimeViewModel = viewModel { Module.topAnimeViewModel() }) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        val items = state.pagingFlow.collectAsLazyPagingItems()
        val refresh = items.loadState.mediator?.refresh

        SwipeRefresh(
            state = rememberSwipeRefreshState(
                isRefreshing = refresh is LoadState.Loading && items.itemCount > 0
            ),
            onRefresh = items::refresh,
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            val _state = rememberLazyListState()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .simpleVerticalScrollbar(_state, 8.dp),
                state = _state
            ) {
                itemsIndexed(items) { position, it ->
                    it?.let {
                        AnimeItem(
                            item = it,
                            position = position,
                            onFavoriteClick = {},
                            modifier = Modifier
                                .clickable { state.actions.onItemClick(it) }
                        )
                    }
                }

                if(items.itemCount > 0) {
                    when (val append = items.loadState.mediator?.append) {
                        is LoadState.Loading -> item(key = "loading") {
                            LoadingItem()
                        }
                        is LoadState.Error -> item(key = "error") {
                            ErrorItem(
                                error = append.error.toCoreError(),
                                onRetry = items::retry
                            )
                        }
                        else -> {}
                    }
                }
            }

            when {
                refresh is LoadState.Loading && items.itemCount == 0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(Dimensions.icon_40)
                        )
                    }
                }
                refresh is LoadState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = refresh.error.toCoreError().localizedMessage(LocalContext.current),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(Dimensions.space_16)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    if (state.showLogoutDialog) {
        LogoutDialog(state = state)
    }
}

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = 1f
            )
        }
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
                    painter = painterResource(R.drawable.ic_star_filled),
                    contentDescription = stringResource(R.string.desc_to_favorite),
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
                    contentDescription = stringResource(R.string.desc_logout),
                    modifier = Modifier
                        .size(24.dp),
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

@Composable
private fun LogoutDialog(state: TopAnimeState) {
    AlertDialog(
        onDismissRequest = state.actions.onDismissLogoutDialog,
        title = {
            Text(
                text = stringResource(R.string.dialog_logout_title),
                style = MaterialTheme.typography.subtitle1
            )
        },
        text = {
            Text(
                text = stringResource(R.string.dialog_logout_message),
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = state.actions.onConfirmLogoutClick) {
                Text(text = stringResource(R.string.dialog_logout_confirm_button))
            }
        },
        dismissButton = {
            TextButton(onClick = state.actions.onDismissLogoutDialog) {
                Text(text = stringResource(R.string.dialog_logout_dismiss_button))
            }
        }
    )
}