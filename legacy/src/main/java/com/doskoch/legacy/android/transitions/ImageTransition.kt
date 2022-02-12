package com.doskoch.legacy.android.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.doskoch.legacy.android.functions.isEqualTo

/**
 * A [Transition] to change [ImageView]'s image with alpha blinking.
 * @param [alphaModifier] value of alpha, used to blink.
 */
class ImageTransition(val alphaModifier: Float = 0.5f) : Transition() {

    companion object {
        const val BACKGROUND = "ImageTransition:background"
        const val IMAGE = "ImageTransition:image"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        values.view?.let { view ->
            values.values[BACKGROUND] = view.background
            if (view is ImageView) {
                values.values[IMAGE] = view.drawable
            }
        }
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {
        val view = endValues?.view

        return if (startValues != null && view != null) {
            val backgroundAnimator = backgroundAnimator(view, startValues, endValues)
            val imageAnimator = imageAnimator(view, startValues, endValues)

            if (backgroundAnimator != null && imageAnimator != null) {
                AnimatorSet().apply { playTogether(backgroundAnimator, imageAnimator) }
            } else {
                backgroundAnimator ?: imageAnimator
            }
        } else {
            null
        }
    }

    private fun backgroundAnimator(view: View,
                                   startValues: TransitionValues,
                                   endValues: TransitionValues): Animator? {
        val startValue = (startValues.values[BACKGROUND] as Drawable?)?.mutate()
        val endValue = (endValues.values[BACKGROUND] as Drawable?)?.mutate()

        return when {
            startValue != null && endValue != null && !startValue.isEqualTo(endValue) -> {
                view.background = startValue
                fromImageToImageAnimator(startValue, endValue) { view.background = endValue }
            }
            startValue != null && endValue == null -> {
                view.background = startValue
                fromImageToNullAnimator(startValue) { view.background = null }
            }
            startValue == null && endValue != null -> {
                fromNullToImageAnimator(endValue) { view.background = endValue }
            }
            else -> null
        }
    }

    private fun imageAnimator(view: View,
                              startValues: TransitionValues,
                              endValues: TransitionValues): Animator? {
        return (view as? ImageView)?.let {
            val startValue = (startValues.values[IMAGE] as Drawable?)?.mutate()
            val endValue = (endValues.values[IMAGE] as Drawable?)?.mutate()

            when {
                startValue != null && endValue != null -> {
                    view.setImageDrawable(startValue)
                    fromImageToImageAnimator(
                        startValue,
                        endValue
                    ) { view.setImageDrawable(endValue) }
                }
                startValue != null && endValue == null -> {
                    view.setImageDrawable(startValue)
                    fromImageToNullAnimator(startValue) { view.setImageDrawable(null) }
                }
                startValue == null && endValue != null -> {
                    fromNullToImageAnimator(endValue) { view.setImageDrawable(endValue) }
                }
                else -> null
            }
        }
    }

    private fun fromImageToImageAnimator(startValue: Drawable,
                                         endValue: Drawable,
                                         applyFunc: () -> Unit): Animator {
        val startIntermediateAlpha = (startValue.alpha * alphaModifier).toInt()
        val endIntermediateAlpha = (endValue.alpha * alphaModifier).toInt()

        return AnimatorSet().apply {
            playSequentially(
                ObjectAnimator.ofInt(startValue, "alpha", startValue.alpha, startIntermediateAlpha)
                    .apply {
                        addListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator?) = Unit

                            override fun onAnimationEnd(animation: Animator?) {
                                applyFunc.invoke()
                            }

                            override fun onAnimationCancel(animation: Animator?) = Unit

                            override fun onAnimationRepeat(animation: Animator?) = Unit
                        })
                    },
                ObjectAnimator.ofInt(endValue, "alpha", endIntermediateAlpha, endValue.alpha)
            )
        }
    }

    private fun fromImageToNullAnimator(startValue: Drawable, applyFunc: () -> Unit): Animator {
        return ObjectAnimator.ofInt(startValue, "alpha", startValue.alpha, 0).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) = Unit

                override fun onAnimationEnd(animation: Animator?) {
                    applyFunc.invoke()
                }

                override fun onAnimationCancel(animation: Animator?) = Unit
                override fun onAnimationRepeat(animation: Animator?) = Unit
            })
        }
    }

    private fun fromNullToImageAnimator(endValue: Drawable, applyFunc: () -> Unit): Animator {
        return ObjectAnimator.ofInt(endValue, "alpha", 0, endValue.alpha).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    applyFunc.invoke()
                }

                override fun onAnimationEnd(animation: Animator?) = Unit

                override fun onAnimationCancel(animation: Animator?) = Unit

                override fun onAnimationRepeat(animation: Animator?) = Unit
            })
        }
    }
}