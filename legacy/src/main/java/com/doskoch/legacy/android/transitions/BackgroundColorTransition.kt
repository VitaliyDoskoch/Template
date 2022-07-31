package com.doskoch.legacy.android.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.doskoch.legacy.android.functions.getDominantColor

/**
 * Smoothly changes the background color and the alpha of views.
 */
class BackgroundColorTransition : Transition() {

    companion object {
        const val COLOR = "BackgroundColorTransition:background_color"
        const val ALPHA = "BackgroundColorTransition:background_alpha"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        values.view?.let { view ->
            values.values[COLOR] = view.backgroundTintList?.defaultColor
                ?: view.background?.getDominantColor() ?: Color.TRANSPARENT
            values.values[ALPHA] = view.background?.alpha ?: 255
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {
        val view = endValues?.view

        return if (startValues != null && view != null) {
            val colorAnimator = colorAnimator(view, startValues, endValues)
            val alphaAnimator = alphaAnimator(view, startValues, endValues)

            if (colorAnimator != null && alphaAnimator != null) {
                AnimatorSet().apply { playTogether(colorAnimator, alphaAnimator) }
            } else {
                colorAnimator ?: alphaAnimator
            }
        } else {
            null
        }
    }

    private fun colorAnimator(view: View,
                              startValues: TransitionValues,
                              endValues: TransitionValues): Animator? {
        @ColorInt
        val startValue = startValues.values[COLOR] as Int

        @ColorInt
        val endValue = endValues.values[COLOR] as Int

        return if (startValue != endValue) {
            view.background.setTint(startValue)
            view.background = view.background.mutate()
            ObjectAnimator.ofArgb(view.background, "tint", startValue, endValue)
        } else {
            null
        }
    }

    private fun alphaAnimator(view: View,
                              startValues: TransitionValues,
                              endValues: TransitionValues): Animator? {
        val startValue = startValues.values[ALPHA] as Int
        val endValue = endValues.values[ALPHA] as Int

        return if (startValue != endValue) {
            view.background.alpha = startValue
            view.background = view.background.mutate()
            ObjectAnimator.ofInt(view.background, "alpha", startValue, endValue)
        } else {
            null
        }
    }
}