package com.extensions.android.components.ui.simplifiedListeners

import android.view.animation.Animation

/**
 * It is just a stub with overridden methods.
 */
interface SimpleAnimationListener : Animation.AnimationListener {

    override fun onAnimationRepeat(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) {}

    override fun onAnimationStart(animation: Animation?) {}
}