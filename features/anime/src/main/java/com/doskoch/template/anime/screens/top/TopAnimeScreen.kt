package com.doskoch.template.anime.screens.top

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.doskoch.legacy.android.viewModel.viewModelFactory
import com.doskoch.template.anime.R
import com.doskoch.template.anime.data.AnimeItem
import com.doskoch.template.anime.di.Module
import com.doskoch.template.core.components.theme.Dimensions
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
        items = vm.items.collectAsLazyPagingItems()
    )
}

@Composable
private fun TopAnimeScreen(
    items: LazyPagingItems<AnimeItem>
) {
    Scaffold(
        topBar = {
            val systemUiController = rememberSystemUiController()
            val statusBarColor = MaterialTheme.colors.primary

            SideEffect {
                systemUiController.setStatusBarColor(statusBarColor)
            }

            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
            ) {

            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            items(items, key = AnimeItem::id) {
                it?.let { AnimeItem(item = it, onFavoriteClick = {}) }
            }
        }
    }
}

@Composable
private fun AnimeItem(
    item: AnimeItem,
    onFavoriteClick: () -> Unit
) {
    Box {
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
                painter = painterResource(if(item.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_start_outline),
                contentDescription = stringResource(R.string.desc_icon_add_to_favorite),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = Dimensions.space_16, bottom = Dimensions.space_16)
                    .clickable(onClick = onFavoriteClick),
                tint = if(item.isFavorite) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground
            )
        }

        Divider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}