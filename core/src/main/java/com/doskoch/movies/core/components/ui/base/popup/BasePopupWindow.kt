package com.doskoch.movies.core.components.ui.base.popup

import android.view.View
import android.widget.PopupWindow
import androidx.annotation.CallSuper
import androidx.annotation.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.doskoch.movies.core.components.rx.DisposablesManager
import io.reactivex.disposables.Disposable

abstract class BasePopupWindow(
    @Dimension(unit = Dimension.PX)
    width: Int,
    @Dimension(unit = Dimension.PX)
    height: Int
) : PopupWindow(width, height), PopupWindow.OnDismissListener, LifecycleOwner {

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    private val disposablesManager by lazy { DisposablesManager { lifecycle } }

    private var onDismissListener: OnDismissListener? = null

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    fun disposeOn(event: Lifecycle.Event, disposable: Disposable) {
        disposablesManager.disposeOn(event, disposable)
    }

    fun disposeUpToEvent(event: Lifecycle.Event) {
        disposablesManager.disposeUpToEvent(event)
    }

    @CallSuper
    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        initPopup()
    }

    @CallSuper
    override fun showAsDropDown(anchor: View?) {
        super.showAsDropDown(anchor)
        initPopup()
    }

    @CallSuper
    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int) {
        super.showAsDropDown(anchor, xoff, yoff)
        initPopup()
    }

    @CallSuper
    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        initPopup()
    }

    private fun initPopup() {
        super.setOnDismissListener(this)

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        onShow()
    }

    open fun onShow() {}

    @CallSuper
    override fun setOnDismissListener(onDismissListener: OnDismissListener?) {
        this.onDismissListener = onDismissListener
    }

    @CallSuper
    override fun onDismiss() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        onDismissListener?.onDismiss()
    }
}