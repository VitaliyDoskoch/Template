package com.extensions.android.components.ui.simplifiedListeners

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

/**
 * It is just a stub with overridden methods.
 */
interface SimpleDrawerListener : DrawerLayout.DrawerListener {

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerClosed(drawerView: View) {}

    override fun onDrawerOpened(drawerView: View) {}

}