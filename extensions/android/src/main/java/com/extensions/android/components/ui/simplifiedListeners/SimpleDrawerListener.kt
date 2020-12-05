package com.extensions.android.components.ui.simplifiedListeners

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

/**
 * It is just a stub with overridden methods.
 */
interface SimpleDrawerListener : DrawerLayout.DrawerListener {

    override fun onDrawerStateChanged(newState: Int) = Unit

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit

    override fun onDrawerClosed(drawerView: View) = Unit

    override fun onDrawerOpened(drawerView: View) = Unit

}