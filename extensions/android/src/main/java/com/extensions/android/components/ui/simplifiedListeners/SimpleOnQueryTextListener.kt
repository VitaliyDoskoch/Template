package com.extensions.android.components.ui.simplifiedListeners

import androidx.appcompat.widget.SearchView

/**
 * It is just a stub with overridden methods.
 */
interface SimpleOnQueryTextListener : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean = false

}