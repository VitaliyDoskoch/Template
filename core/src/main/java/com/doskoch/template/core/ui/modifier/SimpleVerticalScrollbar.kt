package com.doskoch.template.core.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlin.math.abs

private const val DISPLAY_TIME = 1_000L

fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 6.dp
) = composed {
    val color = MaterialTheme.colors.primary.copy(alpha = 0.75f)

    val targetAlpha = remember(state) { mutableStateOf(1f) }

    val alpha by animateFloatAsState(
        targetValue = targetAlpha.value,
        animationSpec = tween(durationMillis = if (targetAlpha.value == 1f) 0 else 500)
    )

    val totalItemsCount by snapshotFlow { state.layoutInfo.totalItemsCount }.collectAsState(initial = 0)

    LaunchedEffect(totalItemsCount, state.isScrollInProgress) {
        targetAlpha.value = 1f
        delay(DISPLAY_TIME)
        ensureActive()
        targetAlpha.value = if (state.isScrollInProgress) 1f else 0f
    }

    drawWithContent {
        drawContent()

        val info = state.layoutInfo

        val allItemsAreFullyVisible = info.visibleItemsInfo.run {
            info.totalItemsCount == size &&
                firstOrNull()?.isFullyVisible(info) ?: true &&
                lastOrNull()?.isFullyVisible(info) ?: true
        }

        if (alpha == 0f || allItemsAreFullyVisible) return@drawWithContent

        val heightPerItemPx = info.viewportSize.height.toFloat() / info.totalItemsCount.coerceAtLeast(1)

        val firstItemInvisiblePart = info.visibleItemsInfo.firstOrNull()
            ?.let { abs(it.offset).toFloat() / it.size } ?: 0f
        val firstItemInvisiblePartHeightPx = firstItemInvisiblePart * heightPerItemPx

        val firstItemVisiblePart = 1 - firstItemInvisiblePart
        val firstItemVisiblePartHeightPx = heightPerItemPx * firstItemVisiblePart

        val lastItemInvisiblePart = info.visibleItemsInfo.lastOrNull()
            ?.let {
                val outOfScreenPartPx = (it.offset + it.size - info.viewportEndOffset).coerceAtLeast(0)
                outOfScreenPartPx.toFloat() / it.size
            } ?: 0f

        val lastItemVisiblePart = 1 - lastItemInvisiblePart
        val lastItemVisiblePartHeightPx = heightPerItemPx * lastItemVisiblePart

        val fullyVisibleItems = info.visibleItemsInfo.filter { it.isFullyVisible(info) }
        val fullyVisibleItemsHeightPx = fullyVisibleItems.size * heightPerItemPx

        val y = state.firstVisibleItemIndex * heightPerItemPx + firstItemInvisiblePartHeightPx

        val widthPx = width.toPx()
        val heightPx = (firstItemVisiblePartHeightPx.takeIf { firstItemVisiblePart < 1f } ?: 0f) +
            lastItemVisiblePartHeightPx +
            fullyVisibleItemsHeightPx

        drawRoundRect(
            color = color,
            topLeft = Offset(size.width - widthPx, y),
            size = Size(widthPx, heightPx),
            alpha = alpha
        )
    }
}

private fun LazyListItemInfo.isFullyVisible(info: LazyListLayoutInfo): Boolean {
    return offset >= info.viewportStartOffset && offset + size <= info.viewportEndOffset
}