package com.doskoch.legacy.functions

import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionManager

/**
 * Creates [transition] on [sceneRoot] and runs [action].
 * @param sceneRoot [ViewGroup], to which [transition] should be attached.
 * @param transition target [Transition].
 * @param endPrevious whether [TransitionManager.endTransitions] should be called before
 * [TransitionManager.beginDelayedTransition] call. Is true by default.
 * @param action action, which should be performed after beginning new [Transition].
 */
fun <V : ViewGroup> withTransition(sceneRoot: V,
                                   transition: Transition,
                                   endPrevious: Boolean = true,
                                   action: V.() -> Unit) {
    if (endPrevious) {
        TransitionManager.endTransitions(sceneRoot)
    }
    TransitionManager.beginDelayedTransition(sceneRoot, transition)
    action.invoke(sceneRoot)
}