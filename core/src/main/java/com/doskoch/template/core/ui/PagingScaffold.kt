package com.doskoch.template.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.doskoch.template.core.R
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.toCoreError
import com.doskoch.template.core.components.theme.Dimensions

@Composable
fun <T : Any> PagingScaffold(
    items: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    loading: @Composable () -> Unit = { DefaultLoading() },
    error: @Composable (CoreError) -> Unit = { DefaultError(it) },
    content: @Composable () -> Unit
) {
    val loadState = items.loadState
    items.itemCount

    Box(modifier = modifier) {
        content()

        when (val refresh = loadState.refresh) {
            is LoadState.Loading -> loading()
            is LoadState.Error -> error(refresh.error.toCoreError())
            else -> {}
        }
    }
}

@Composable
private fun DefaultLoading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(Dimensions.icon_40)
        )
    }
}

@Composable
private fun DefaultError(error: CoreError) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
fun LoadingItem() {
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
fun ErrorItem(error: CoreError, onRetry: () -> Unit) {
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