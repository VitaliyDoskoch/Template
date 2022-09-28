package com.doskoch.legacy.android.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.graphics.ColorUtils
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * Smoothly changes the inputType of EditTexts' with the alpha blinking.
 */
class InputTypeTransition(private val alphaModifier: Float = 0.5f) : Transition() {

    companion object {
        const val INPUT_TYPE = "InputTypeTransition:input_type"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        (values.view as? EditText)?.let {
            values.values[INPUT_TYPE] = it.inputType
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val view = endValues?.view as? EditText

        return if (startValues != null && view != null) {
            val startValue = startValues.values[INPUT_TYPE] as Int
            val endValue = endValues.values[INPUT_TYPE] as Int

            if (startValue != endValue) {
                val initialTypeFace = view.typeface
                val selectionEnd = view.selectionEnd

                view.inputType = startValue
                view.typeface = initialTypeFace
                view.setSelection(selectionEnd)

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

                        override fun onAnimationStart(animation: Animator) = Unit

                        override fun onAnimationEnd(animation: Animator) {
                            view.setTextColor(initialColor)
                            if (!applied) {
                                view.inputType = endValue
                                view.typeface = initialTypeFace
                                view.setSelection(selectionEnd)
                            }
                        }

                        override fun onAnimationCancel(animation: Animator) = Unit

                        override fun onAnimationRepeat(animation: Animator) {
                            view.inputType = endValue
                            view.typeface = initialTypeFace
                            view.setSelection(selectionEnd)
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
