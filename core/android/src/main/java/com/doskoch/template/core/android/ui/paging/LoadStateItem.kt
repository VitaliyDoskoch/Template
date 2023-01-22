package com.doskoch.template.core.android.ui.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.paging.LoadState
import com.doskoch.template.core.android.R
import com.doskoch.template.core.android.components.error.CoreError
import com.doskoch.template.core.android.components.error.toCoreError
import com.doskoch.template.core.android.ui.common.components.CoreButton
import com.doskoch.template.core.android.ui.theme.Dimensions

@Suppress("FunctionName")
fun LazyListScope.LoadStateItem(
    itemCount: Int,
    loadState: LoadState,
    onRetry: () -> Unit,
    loadingItem: @Composable LazyItemScope.() -> Unit = { LoadingItem() },
    errorItem: @Composable LazyItemScope.(CoreError) -> Unit = { ErrorItem(it, onRetry) }
) {
    if (itemCount > 0) {
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
            text = stringResource(R.string.common_retry),
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
