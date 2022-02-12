package com.doskoch.legacy.android.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * A [Transition] to change [android.view.View]'s alpha smoothly.
 */
class AlphaTransition : Transition() {

    companion object {
        const val ALPHA = "AlphaTransition:alpha"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        values.view?.let {
            values.values[ALPHA] = it.alpha
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {
        val view = endValues?.view

        return if (startValues != null && view != null) {
            val startValue = startValues.values[ALPHA] as Float
            val endValue = endValues.values[ALPHA] as Float

            if (startValue != endValue) {
                view.alpha = startValue
                ObjectAnimator.ofFloat(view, "alpha", startValue, endValue)
            } else {
                null
            }
        } else {
            null
        }
    }
}