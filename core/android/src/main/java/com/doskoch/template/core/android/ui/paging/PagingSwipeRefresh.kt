package com.doskoch.template.core.android.ui.paging

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PagingSwipeRefresh(
    loadState: LoadState?,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    swipeEnabled: Boolean = true,
    refreshTriggerDistance: Dp = 80.dp,
    indicatorAlignment: Alignment = Alignment.TopCenter,
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    indicator: @Composable (state: SwipeRefreshState, refreshTrigger: Dp) -> Unit = { s, trigger -> SwipeRefreshIndicator(s, trigger) },
    clipIndicatorToPadding: Boolean = true,
    content: @Composable (SwipeRefreshState) -> Unit
) {
    val showIndicator = remember { mutableStateOf(false) }

    LaunchedEffect(loadState) {
        if (loadState !is LoadState.Loading) {
            showIndicator.value = false
        }
    }

    val state = rememberSwipeRefreshState(isRefreshing = showIndicator.value)

    SwipeRefresh(
        state = state,
        onRefresh = {
            showIndicator.value = true
            onRefresh()
        },
        modifier = modifier,
        swipeEnabled = swipeEnabled,
        refreshTriggerDistance = refreshTriggerDistance,
        indicatorAlignment = indicatorAlignment,
        indicatorPadding = indicatorPadding,
        indicator = indicator,
        clipIndicatorToPadding = clipIndicatorToPadding,
        content = { content(state) }
    )
}
