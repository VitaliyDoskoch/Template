package com.doskoch.legacy.android.swipeRefreshLayout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doskoch.legacy.R
import com.doskoch.legacy.android.functions.getThemeColor
import kotlin.math.abs

class CoreSwipeRefreshLayout(context: Context, attrs: AttributeSet? = null) :
    SwipeRefreshLayout(context, attrs) {

    private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var previousX: Float = 0f
    private var previousY: Float = 0f

    init {
        setColorSchemeColors(
            context.getThemeColor(R.attr.colorPrimary),
            context.getThemeColor(R.attr.colorAccent)
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