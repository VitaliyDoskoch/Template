package com.extensions.android.components.ui.simplifiedListeners

import androidx.transition.Transition

/**
 * It is just a stub with overridden methods.
 */
interface SimpleTransitionListener : Transition.TransitionListener {

    override fun onTransitionEnd(transition: Transition) = Unit

    override fun onTransitionResume(transition: Transition) = Unit

    override fun onTransitionPause(transition: Transition) = Unit

    override fun onTransitionCancel(transition: Transition) = Unit

    override fun onTransitionStart(transition: Transition) = Unit
}