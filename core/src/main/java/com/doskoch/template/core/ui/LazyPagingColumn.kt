package com.doskoch.template.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.doskoch.template.core.R
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.components.theme.Dimensions

@Composable
fun <T : Any> LazyPagingColumn(
    items: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loading: @Composable BoxScope.() -> Unit = { DefaultLoading() },
    error: @Composable BoxScope.(CoreError) -> Unit = { DefaultError(it) },
    errorOnContent: @Composable BoxScope.(LoadState.Error) -> Unit = { DefaultErrorOnContent(it) },
    content: LazyListScope.() -> Unit
) {
    Box {
        LazyColumn(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = content
        )

        val refresh = items.loadState.refresh

        when {
            refresh is LoadState.Loading && items.itemCount == 0 -> loading()
            refresh is LoadState.Error -> {
                if(items.itemCount == 0) {
                    error(refresh.error.toCoreError())
                } else {
                    errorOnContent(refresh)
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
private fun BoxScope.DefaultErrorOnContent(state: LoadState.Error) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(state) {
        snackbarHostState.showSnackbar(state.error.toCoreError().localizedMessage(context))
    }

    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
}

fun LazyListScope.LoadStateItem(
    itemCount: Int,
    loadState: LoadState,
    onRetry: () -> Unit,
    loadingItem: @Composable LazyItemScope.() -> Unit = { LoadingItem() },
    errorItem: @Composable LazyItemScope.(CoreError) -> Unit = { ErrorItem(it, onRetry) }
) {
    if(itemCount > 0) {
        when (loadState) {
            is LoadState.Loading -> item(key = "loading") { loadingItem() }
            is LoadState.Error -> item(key = "error") { errorItem(loadState.error.toCoreError()) }
            else -> {}
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(Dimensions.icon_40)
        )
    }
}

@Composable
private fun ErrorItem(error: CoreError, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = error.localizedMessage(LocalContext.current),
            modifier = Modifier
                .padding(start = Dimensions.space_16, top = Dimensions.space_16, end = Dimensions.space_16)
                .fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )

        CoreButton(
            text = stringResource(R.string.retry),
            onClick = onRetry,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(Dimensions.space_16)
                .width(IntrinsicSize.Min)
                .requiredHeight(Dimensions.button_height),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            )
        )
    }
}