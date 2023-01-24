package com.doskoch.template.anime.presentation.screens.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.doskoch.template.anime.presentation.R
import com.doskoch.template.anime.presentation.di.AnimeFeatureInjector
import com.doskoch.template.anime.presentation.screens.details.AnimeDetailsState.ScreenState
import com.doskoch.template.core.android.ui.theme.Dimensions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.DecimalFormat

@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    title: String,
    vm: AnimeDetailsViewModel = viewModel { AnimeFeatureInjector.animeDetailsViewModelFactory().create(animeId, title) }
) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(state = state)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (state.screenState) {
                is ScreenState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(Dimensions.icon_40)
                    )
                }
                is ScreenState.Content -> {
                    Content(content = state.screenState)
                }
                is ScreenState.Error -> {
                    Text(
                        text = state.screenState.error.localizedMessage(LocalContext.current),
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

@Composable
private fun TopBar(state: AnimeDetailsState) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colors.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
    }

    TopAppBar(
        modifier = Modifier
            .statusBarsPadding(),
        title = {
            Text(
                text = state.title,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = state.actions.onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_desc_navigate_back),
                    modifier = Modifier
                        .size(Dimensions.icon_24)
                )
            }
        },
        actions = {
            IconButton(
                onClick = state.actions.onFavoriteClick
            ) {
                Icon(
                    painter = painterResource(if (state.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_start_outline),
                    contentDescription = stringResource(R.string.anime_feature_desc_to_favorite),
                    modifier = Modifier
                        .size(Dimensions.icon_24),
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    )
}

@Composable
private fun Content(content: ScreenState.Content) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PrimaryInfoRow(
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.space_16)
        )

        Description(
            text = content.description,
            modifier = Modifier
                .padding(horizontal = Dimensions.space_16)
                .fillMaxWidth()
        )

        SecondaryInfoRow(
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.space_16)
        )
    }
}

@Composable
private fun PrimaryInfoRow(
    content: ScreenState.Content,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        AsyncImage(
            model = content.imageUrl,
            contentDescription = stringResource(R.string.anime_feature_desc_anime_image),
            modifier = Modifier
                .size(120.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_photo),
            error = painterResource(R.drawable.ic_sync_problem)
        )

        Column(
            modifier = Modifier
                .padding(start = Dimensions.space_16)
                .height(120.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Hint(text = stringResource(R.string.anime_details_screen_score_hint))
            Hint(text = stringResource(R.string.anime_details_screen_ranked_hint))
            Hint(text = stringResource(R.string.anime_details_screen_popularity_hint))
            Hint(text = stringResource(R.string.anime_details_screen_status_hint))
        }

        Column(
            modifier = Modifier
                .padding(start = Dimensions.space_16)
                .height(120.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            FieldValue(
                text = buildString {
                    val score = DecimalFormat("#.#").format(content.score)
                    append("$score ")
                    append(LocalContext.current.resources.getQuantityString(R.plurals.anime_feature_by_users, content.scoredBy, content.scoredBy))
                }
            )
            FieldValue(text = content.rank.toString())
            FieldValue(text = content.popularity.toString())
            FieldValue(text = content.status)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Description(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val expanded = remember { mutableStateOf(false) }

        Text(
            text = text,
            modifier = Modifier
                .animateContentSize(),
            style = MaterialTheme.typography.body2,
            maxLines = if (expanded.value) Int.MAX_VALUE else 5,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier
                .padding(top = Dimensions.space_16)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.primary)
                .clickable { expanded.value = !expanded.value }
        ) {
            Text(
                text = stringResource(if (expanded.value) R.string.common_show_less else R.string.common_show_more),
                modifier = Modifier
                    .padding(horizontal = Dimensions.space_16, vertical = Dimensions.space_4),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SecondaryInfoRow(
    content: ScreenState.Content,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        val paddingModifier = Modifier.padding(top = Dimensions.space_6)

        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Hint(text = stringResource(R.string.anime_details_screen_type_hint))
            Hint(text = stringResource(R.string.anime_details_screen_genres_hint), paddingModifier)
            Hint(text = stringResource(R.string.anime_details_screen_theme_hint), paddingModifier)
            Hint(text = stringResource(R.string.anime_details_screen_rating_hint), paddingModifier)
            Hint(text = stringResource(R.string.anime_details_screen_episodes_hint), paddingModifier)
            Hint(text = stringResource(R.string.anime_details_screen_duration_hint), paddingModifier)
            Hint(text = stringResource(R.string.anime_details_screen_studios_hint), paddingModifier)
        }

        Column(
            modifier = Modifier
                .padding(start = Dimensions.space_16)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            FieldValue(text = content.type)
            FieldValue(text = content.genres.joinToString(separator = ", "), paddingModifier)
            FieldValue(text = content.themes.joinToString(separator = ", "), paddingModifier)
            FieldValue(text = content.rating, paddingModifier)
            FieldValue(text = content.episodes.toString(), paddingModifier)
            FieldValue(text = content.duration, paddingModifier)
            FieldValue(text = content.studios.joinToString(separator = ", "), paddingModifier)
        }
    }
}

@Composable
private fun Hint(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$text:",
        modifier = modifier,
        style = MaterialTheme.typography.subtitle2,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun FieldValue(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.body2,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
