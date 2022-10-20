package com.doskoch.template.core.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

fun Modifier._simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier = composed {
    val targetAlpha = 1f//if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = true//state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}

fun Modifier.simpleVerticalScrollbar(state: LazyListState) = composed {
    val color = MaterialTheme.colors.primary

    drawWithContent {
        drawContent()

        val info = state.layoutInfo

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

        val widthPx = 8.dp.toPx()
        val heightPx = (firstItemVisiblePartHeightPx.takeIf { firstItemVisiblePart < 1f } ?: 0f) +
            lastItemVisiblePartHeightPx +
            fullyVisibleItemsHeightPx

        drawRoundRect(
            color = color,
            topLeft = Offset(size.width - widthPx, y),
            size = Size(widthPx, heightPx)
        )
    }
}

private fun LazyListItemInfo.isFullyVisible(info: LazyListLayoutInfo): Boolean {
    return offset >= info.viewportStartOffset && offset + size <= info.viewportEndOffset
}