package com.extensions.android.components.ui.simplifiedListeners

import androidx.transition.Transition

/**
 * It is just a stub with overridden methods.
 */
interface SimpleTransitionListener : Transition.TransitionListener {

    override fun onTransitionEnd(transition: Transition) {}

    override fun onTransitionResume(transition: Transition) {}

    override fun onTransitionPause(transition: Transition) {}

    override fun onTransitionCancel(transition: Transition) {}

    override fun onTransitionStart(transition: Transition) {}
}