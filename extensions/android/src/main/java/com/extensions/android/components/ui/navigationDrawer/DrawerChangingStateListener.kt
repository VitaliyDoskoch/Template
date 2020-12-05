package com.extensions.android.components.ui.navigationDrawer

import android.view.View
import androidx.annotation.CallSuper
import com.extensions.android.components.ui.simplifiedListeners.SimpleDrawerListener

/**
 * Allows to observe whether NavigationDrawer starts to open or close.
 */
abstract class DrawerChangingStateListener : SimpleDrawerListener {

    open fun onDrawerStartOpening() = Unit
    open fun onDrawerStartClosing() = Unit

    private var skipIncreasing = false
    private var skipDecreasing = false
    private var lastOffset = 0f

    @CallSuper
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        super.onDrawerSlide(drawerView, slideOffset)

        when {
            slideOffset > lastOffset && !skipIncreasing -> {
                skipIncreasing = true
                skipDecreasing = false
                onDrawerStartOpening()
            }
            slideOffset < lastOffset && !skipDecreasing -> {
                skipDecreasing = true
                skipIncreasing = false
                onDrawerStartClosing()
            }
        }

        lastOffset = slideOffset
    }

}