package com.doskoch.template.core.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import timber.log.Timber
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

        val widthPx = 8.dp.toPx()
        val heightPerItemPx = size.height / state.layoutInfo.totalItemsCount
        val firstItemOffsetPx = state.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
            heightPerItemPx * (abs(it.offset).toFloat() / it.size)
        } ?: 0f

        val lastItemOffsetPx = state.layoutInfo.visibleItemsInfo.lastOrNull()?.let {
            heightPerItemPx * (abs(state.layoutInfo.viewportEndOffset - (it.offset + it.size)).toFloat() / it.size)
        } ?: 0f

        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width - widthPx,
                y = state.firstVisibleItemIndex * heightPerItemPx + firstItemOffsetPx
            ),
            size = Size(
                width = widthPx,
                height = state.layoutInfo.visibleItemsInfo.filter { it.offset + it.size < state.layoutInfo.viewportEndOffset }.size * heightPerItemPx + firstItemOffsetPx + lastItemOffsetPx
            ),
            cornerRadius = CornerRadius(2.dp.toPx())
        )
    }
}