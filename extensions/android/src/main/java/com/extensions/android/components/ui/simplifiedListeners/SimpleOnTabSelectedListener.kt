package com.extensions.android.components.ui.simplifiedListeners

import com.google.android.material.tabs.TabLayout

/**
 * It is just a stub with overridden methods.
 */
interface SimpleOnTabSelectedListener : TabLayout.OnTabSelectedListener {

    override fun onTabReselected(p0: TabLayout.Tab?) = Unit

    override fun onTabUnselected(p0: TabLayout.Tab?) = Unit

    override fun onTabSelected(p0: TabLayout.Tab?) = Unit
}