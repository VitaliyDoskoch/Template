package com.doskoch.legacy.android.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * A [Transition] to change [TextView]'s text with alpha blinking.
 * @param [alphaModifier] value of alpha, used to blink.
 */
class TextTransition(val alphaModifier: Float = 0.5f) : Transition() {

    companion object {
        const val TEXT = "TextTransition:text"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        (values.view as? TextView)?.let {
            values.values[TEXT] = it.text.toString()
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {
        val view = endValues?.view as? TextView

        return if (startValues != null && view != null) {
            val startValue = startValues.values[TEXT] as String
            val endValue = endValues.values[TEXT] as String

            if (startValue != endValue) {
                view.text = startValue

                val initialColor = view.currentTextColor
                val intermediateColor = ColorUtils.setAlphaComponent(
                    initialColor,
                    (Color.alpha(initialColor) * alphaModifier).toInt()
                )

                ObjectAnimator.ofArgb(view, "textColor", initialColor, intermediateColor).apply {
                    repeatCount = 1
                    repeatMode = ValueAnimator.REVERSE
                    addListener(object : Animator.AnimatorListener {

                        var applied = false

                        override fun onAnimationStart(animation: Animator?) = Unit

                        override fun onAnimationEnd(animation: Animator?) {
                            view.setTextColor(initialColor)
                            if (!applied) {
                                view.text = endValue
                            }
                        }

                        override fun onAnimationCancel(animation: Animator?) = Unit

                        override fun onAnimationRepeat(animation: Animator?) {
                            view.text = endValue
                            applied = true
                        }
                    })
                }
            } else {
                null
            }
        } else {
            null
        }
    }
}