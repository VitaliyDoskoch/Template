package com.doskoch.template.core.ui.paging

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.doskoch.template.core.R
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.components.theme.Dimensions

private sealed class ScreenContent {
    object List : ScreenContent()
    object Loading : ScreenContent()
    object LoadingOverList : ScreenContent()
    data class Error(val loadState: LoadState.Error) : ScreenContent()
    data class ErrorOverList(val loadState: LoadState.Error) : ScreenContent()
    object Placeholder : ScreenContent()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PagingScaffold(
    itemCount: Int,
    loadState: CombinedLoadStates?,
    modifier: Modifier,
    loading: @Composable BoxScope.() -> Unit = { DefaultLoading() },
    loadingOverList: @Composable BoxScope.() -> Unit = {},
    error: @Composable BoxScope.(LoadState.Error) -> Unit = { DefaultError(it.error.toCoreError()) },
    errorOverList: @Composable BoxScope.(LoadState.Error) -> Unit = { DefaultErrorOverContent(it) },
    placeholder: @Composable BoxScope.() -> Unit = { DefaultPlaceholder() },
    list: @Composable BoxScope.() -> Unit
) {
    Box(modifier) {
        val screenContent = remember { mutableStateOf<ScreenContent>(ScreenContent.List) }

        SideEffect {
            val refresh = loadState?.refresh
            val prepend = loadState?.prepend
            val append = loadState?.append

            screenContent.value = when {
                refresh is LoadState.Loading -> if (itemCount == 0) ScreenContent.Loading else ScreenContent.LoadingOverList
                refresh is LoadState.Error -> if (itemCount == 0) ScreenContent.Error(refresh) else ScreenContent.ErrorOverList(refresh)
                itemCount == 0 && refresh is LoadState.NotLoading &&
                    prepend is LoadState.NotLoading && prepend.endOfPaginationReached &&
                    append is LoadState.NotLoading && append.endOfPaginationReached -> ScreenContent.Placeholder
                else -> ScreenContent.List
            }
        }

        AnimatedContent(
            targetState = screenContent.value,
            modifier = Modifier
                .matchParentSize(),
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 500)) with fadeOut(animationSpec = tween(durationMillis = 50))
            }
        ) { content ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (content) {
                    is ScreenContent.List,
                    is ScreenContent.LoadingOverList,
                    is ScreenContent.ErrorOverList -> {
                        list()
                        when (content) {
                            is ScreenContent.LoadingOverList -> loadingOverList()
                            is ScreenContent.ErrorOverList -> errorOverList(content.loadState)
                            else -> {}
                        }
                    }
                    is ScreenContent.Loading -> loading()
                    is ScreenContent.Error -> error(content.loadState)
                    is ScreenContent.Placeholder -> placeholder()
                }
            }
        }
    }
}

@Composable
private fun BoxScope.DefaultLoading() {
    Box(
        modifier = Modifier.matchParentSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(Dimensions.icon_40)
        )
    }
}

@Composable
private fun BoxScope.DefaultError(error: CoreError) {
    Box(
        modifier = Modifier
            .matchParentSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = error.localizedMessage(LocalContext.current),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(Dimensions.space_16)
                .fillMaxWidth(),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BoxScope.DefaultErrorOverContent(state: LoadState.Error) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(state) {
        snackbarHostState.showSnackbar(state.error.toCoreError().localizedMessage(context))
    }

    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
}

@Composable
private fun BoxScope.DefaultPlaceholder() {
    Column(
        modifier = Modifier
            .matchParentSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.im_cat),
            contentDescription = stringResource(R.string.common_desc_empty_list),
            modifier = Modifier
                .size(200.dp)
        )

        Text(
            text = stringResource(R.string.common_placeholder_empty_list),
            modifier = Modifier
                .padding(Dimensions.space_16)
                .fillMaxWidth(),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}
