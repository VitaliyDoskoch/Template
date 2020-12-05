package com.extensions.android.components.ui.simplifiedListeners

import android.animation.Animator

/**
 * It is just a stub with overridden methods.
 */
interface SimpleAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) = Unit

    override fun onAnimationEnd(animation: Animator?) = Unit

    override fun onAnimationCancel(animation: Animator?) = Unit

    override fun onAnimationStart(animation: Animator?) = Unit
}