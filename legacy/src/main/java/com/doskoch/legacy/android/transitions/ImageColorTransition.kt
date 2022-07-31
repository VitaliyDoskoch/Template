package com.doskoch.legacy.android.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.doskoch.legacy.android.functions.getDominantColor

/**
 * Smoothly changes the image color and the alpha of ImageViews.
 */
class ImageColorTransition : Transition() {

    companion object {
        const val COLOR = "ImageColorTransition:image_color"
        const val ALPHA = "ImageColorTransition:image_alpha"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        (values.view as? ImageView)?.let { view ->
            values.values[COLOR] = view.imageTintList?.defaultColor
                ?: view.drawable?.getDominantColor() ?: Color.TRANSPARENT
            values.values[ALPHA] = view.drawable?.alpha ?: 0
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val view = endValues?.view as? ImageView

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

    private fun colorAnimator(
        view: ImageView,
        startValues: TransitionValues,
        endValues: TransitionValues
    ): Animator? {
        @ColorInt
        val startValue = startValues.values[COLOR] as Int

        @ColorInt
        val endValue = endValues.values[COLOR] as Int

        return if (startValue != endValue) {
            view.drawable.setTint(startValue)
            view.setImageDrawable(view.drawable.mutate())
            ObjectAnimator.ofArgb(view.drawable, "tint", startValue, endValue)
        } else {
            null
        }
    }

    private fun alphaAnimator(
        view: ImageView,
        startValues: TransitionValues,
        endValues: TransitionValues
    ): Animator? {
        val startValue = startValues.values[ALPHA] as Int
        val endValue = endValues.values[ALPHA] as Int

        return if (startValue != endValue) {
            view.drawable.alpha = startValue
            view.setImageDrawable(view.drawable.mutate())
            ObjectAnimator.ofInt(view.drawable, "alpha", startValue, endValue)
        } else {
            null
        }
    }
}
