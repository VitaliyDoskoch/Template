package com.doskoch.template.core.ui.modifier

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.simpleVerticalScrollbar(state: LazyListState, width: Dp = 8.dp): Modifier = drawWithContent {
    drawContent()

    val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

    // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
    if (firstVisibleElementIndex != null) {
        val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
        val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
        val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

        drawRect(
            color = Color.Red,
            topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
            size = Size(width.toPx(), scrollbarHeight),
            alpha = 1f
        )
    }
}