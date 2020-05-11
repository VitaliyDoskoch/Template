package com.extensions.android.components.ui.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * A [Transition] to change [TextView]'s text background color and alpha smoothly.
 */
class TextColorTransition : Transition() {

    companion object {
        const val TEXT_COLOR = "TextColorTransition:text_color"
        const val HINT_COLOR = "TextColorTransition:hint_color"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        (values.view as? TextView)?.let {
            values.values[TEXT_COLOR] = it.currentTextColor
            values.values[HINT_COLOR] = it.currentHintTextColor
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {
        val view = endValues?.view as? TextView

        return if (startValues != null && view != null) {
            val textColorAnimator = textColorAnimator(view, startValues, endValues)
            val hintColorAnimator = hintColorAnimator(view, startValues, endValues)

            if (textColorAnimator != null && hintColorAnimator != null) {
                AnimatorSet().apply { playTogether(textColorAnimator, hintColorAnimator) }
            } else {
                textColorAnimator ?: hintColorAnimator
            }
        } else {
            null
        }
    }

    private fun textColorAnimator(view: TextView,
                                  startValues: TransitionValues,
                                  endValues: TransitionValues): Animator? {
        @ColorInt
        val startValue = startValues.values[TEXT_COLOR] as Int

        @ColorInt
        val endValue = endValues.values[TEXT_COLOR] as Int

        return if (startValue != endValue) {
            view.setTextColor(startValue)
            ObjectAnimator.ofArgb(view, "textColor", startValue, endValue)
        } else {
            null
        }
    }

    private fun hintColorAnimator(view: TextView,
                                  startValues: TransitionValues,
                                  endValues: TransitionValues): Animator? {
        @ColorInt
        val startValue = startValues.values[HINT_COLOR] as Int

        @ColorInt
        val endValue = endValues.values[HINT_COLOR] as Int

        return if (startValue != endValue) {
            view.setHintTextColor(startValue)
            ObjectAnimator.ofArgb(view, "hintTextColor", startValue, endValue)
        } else {
            null
        }
    }
}