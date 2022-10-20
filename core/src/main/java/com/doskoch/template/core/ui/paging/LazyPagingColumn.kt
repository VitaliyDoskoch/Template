package com.doskoch.template.core.ui.paging

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.doskoch.template.core.ui.FadeInOnCompose
import timber.log.Timber

@Composable
fun LazyPagingColumn(
    itemCount: Int,
    loadStates: CombinedLoadStates?,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loading: @Composable BoxScope.(LoadState.Loading) -> Unit = { DefaultLoading() },
    loadingOverContent: @Composable BoxScope.(LoadState.Loading) -> Unit = {},
    error: @Composable BoxScope.(LoadState.Error) -> Unit = { DefaultError(it.error.toCoreError()) },
    errorOverContent: @Composable BoxScope.(LoadState.Error) -> Unit = { DefaultErrorOverContent(it) },
    placeholder: @Composable BoxScope.(LoadState.NotLoading) -> Unit = { DefaultPlaceholder() },
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

        val refresh = loadStates?.refresh
        val prepend = loadStates?.prepend
        val append = loadStates?.append

        when {
            refresh is LoadState.Loading -> {
                if (itemCount == 0) {
                    loading(refresh)
                } else {
                    loadingOverContent(refresh)
                }
            }
            refresh is LoadState.Error -> {
                if (itemCount == 0) {
                    error(refresh)
                } else {
                    errorOverContent(refresh)
                }
            }
            refresh is LoadState.NotLoading && prepend is LoadState.NotLoading && append is LoadState.NotLoading -> {
                if(prepend.endOfPaginationReached && append.endOfPaginationReached && itemCount == 0) {
                    placeholder(refresh)
                }
            }
        }
    }
}

@Composable
private fun BoxScope.DefaultLoading() {
    FadeInOnCompose(
        modifier = Modifier.matchParentSize()
    ) {
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
}

@Composable
private fun BoxScope.DefaultError(error: CoreError) {
    FadeInOnCompose(
        modifier = Modifier.matchParentSize()
    ) {
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
    FadeInOnCompose(
        modifier = Modifier.matchParentSize()
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.im_cat),
                contentDescription = stringResource(R.string.desc_placeholder_empty_list),
                modifier = Modifier
                    .size(200.dp)
            )

            Text(
                text = stringResource(R.string.placeholder_empty_list),
                modifier = Modifier
                    .padding(Dimensions.space_16)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}