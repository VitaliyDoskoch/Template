package com.extensions.android.components.ui.simplifiedListeners

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

/**
 * It is just a stub with overridden methods.
 */
abstract class SimpleAdapterDataObserver : RecyclerView.AdapterDataObserver() {

    open fun onAnyChange() = Unit

    @CallSuper
    override fun onChanged() {
        onAnyChange()
    }

    @CallSuper
    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        onAnyChange()
    }

    @CallSuper
    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        onAnyChange()
    }

    @CallSuper
    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        onAnyChange()
    }

    @CallSuper
    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        onAnyChange()
    }

    @CallSuper
    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        onAnyChange()
    }

}