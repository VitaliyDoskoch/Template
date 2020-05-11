package com.extensions.android.components.ui.simplifiedListeners

import android.animation.Animator

/**
 * It is just a stub with overridden methods.
 */
interface SimpleAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {}

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationStart(animation: Animator?) {}
}