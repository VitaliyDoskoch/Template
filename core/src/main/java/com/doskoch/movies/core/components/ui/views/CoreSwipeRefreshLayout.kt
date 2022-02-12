package com.doskoch.movies.core.components.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doskoch.legacy.android.functions.getThemeColor
import com.doskoch.movies.core.R
import kotlin.math.abs

class CoreSwipeRefreshLayout(context: Context, attrs: AttributeSet? = null) :
    SwipeRefreshLayout(context, attrs) {

    private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var previousX: Float = 0f
    private var previousY: Float = 0f

    init {
        setColorSchemeColors(
            context.getThemeColor(R.attr.colorPrimaryLight),
            context.getThemeColor(R.attr.colorPrimaryDark)
        )
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                previousX = event.x
                previousY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val xDelta = abs(previousX - event.x)
                val yDelta = abs(previousY - event.y)

                if (yDelta < touchSlop || xDelta > touchSlop || xDelta > yDelta) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }
}