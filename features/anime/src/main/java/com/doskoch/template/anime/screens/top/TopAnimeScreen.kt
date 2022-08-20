package com.doskoch.template.anime.screens.top

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.Divider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.doskoch.legacy.android.viewModel.viewModelFactory
import com.doskoch.template.anime.R
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.data.AnimeType
import com.doskoch.template.anime.data.stringId
import com.doskoch.template.anime.di.Module
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.components.theme.Dimensions
import com.doskoch.template.core.components.ui.ErrorItem
import com.doskoch.template.core.components.ui.LoadingItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.DecimalFormat

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TopAnimeScreen() {
    TopAnimeScreen(
        vm = viewModel(factory = viewModelFactory { Module.topAnimeViewModel })
    )
}

@Composable
private fun TopAnimeScreen(vm: TopAnimeViewModel) {
    TopAnimeScreen(
        state = vm.state.collectAsState().value
    )
}

@Composable
private fun TopAnimeScreen(
    state: TopAnimeViewModel.State
) {
    ScreenContent(state = state)

    if(state.showLogoutDialog) {
        LogoutDialog(state = state)
    }
}

@Composable
private fun ScreenContent(state: TopAnimeViewModel.State) {
    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        val items = state.pagingData.collectAsLazyPagingItems()
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
                refresh is LoadState.NotLoading || items.itemCount > 0 -> {
                    val append = items.loadState.mediator?.append

                    val _state = rememberLazyListState()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .simpleVerticalScrollbar(_state, 8.dp),
                        state = _state
                    ) {
                        items(items) {
                            it?.let {
                                AnimeItem(
                                    item = it,
                                    onFavoriteClick = {},
                                    modifier = Modifier
                                        .clickable { state.actions.onItemClick(it) }
                                )
                            }
                        }

                        when {
                            append is LoadState.Loading -> item(key = "loading") {
                                LoadingItem()
                            }
                            append is LoadState.Error -> item(key = "error") {
                                ErrorItem(
                                    error = append.error.toCoreError(),
                                    onRetry = items::retry
                                )
                            }
                        }
                    }
                }
            }
        }
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
private fun TopBar(state: TopAnimeViewModel.State) {
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
private fun ColumnScope.AnimeTypeItems(state: TopAnimeViewModel.State) {
    AnimeType.values().forEach {
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
private fun AnimeItem(
    item: AnimeItem,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = stringResource(R.string.desc_item_image),
                modifier = Modifier
                    .padding(start = Dimensions.space_16, top = Dimensions.space_16, bottom = Dimensions.space_16)
                    .size(80.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_photo),
                error = painterResource(R.drawable.ic_sync_problem)
            )

            Column(
                modifier = Modifier
                    .padding(Dimensions.space_16)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = item.title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.genres.joinToString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = buildAnnotatedString {
                        val score = DecimalFormat("#.#").format(item.score)
                        append("$score ")
                        addStyle(SpanStyle(fontWeight = FontWeight.Bold), 0, score.length)

                        append(LocalContext.current.resources.getQuantityString(R.plurals.by_users, item.scoredBy, item.scoredBy))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                painter = painterResource(if (item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_start_outline),
                contentDescription = stringResource(R.string.desc_icon_add_to_favorite),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = Dimensions.space_16, bottom = Dimensions.space_16)
                    .clickable(onClick = onFavoriteClick),
                tint = if (item.isFavorite) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground
            )
        }

        Divider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun LogoutDialog(state: TopAnimeViewModel.State) {
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