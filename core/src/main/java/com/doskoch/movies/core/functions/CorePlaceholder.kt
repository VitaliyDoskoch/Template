package com.doskoch.movies.core.functions

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.doskoch.movies.core.R
import com.doskoch.movies.core.components.ui.views.CorePlaceholder
import com.extensions.retrofit.components.exceptions.NetworkException
import com.extensions.retrofit.components.exceptions.NoInternetException

fun CorePlaceholder.setImage(image: CorePlaceholder.Image) {
    binding.imageView.setImageResource(image.drawableRes)
    setImageDimensionRatio(image.ratio)
}

fun CorePlaceholder.setImageDimensionRatio(ratio: String) {
    ConstraintSet().let {
        it.clone(this)
        it.setDimensionRatio(R.id.imageView, ratio)
        it.applyTo(this)
    }
}

/**
 * Prepares [CorePlaceholder] to simply show an image and a message (also hides actionButton).
 */
fun CorePlaceholder.showSimple(image: CorePlaceholder.Image, message: String) {
    with(binding) {
        setImage(image)
        imageView.isVisible = true

        messageTextView.text = message
        messageTextView.isVisible = true

        actionButton.isVisible = false
    }
}

/**
 * Prepares [CorePlaceholder] to display an error.
 * @param throwable Throwable to find corresponding image and message.
 * @param actionButtonText text, that will be shown in actionButton. Is null by default.
 * @param action function, that will be triggered on actionButton click. Is null by default.
 * NOTE: if both [actionButtonText] and [action] are null, actionButton will be hidden.
 */
fun CorePlaceholder.showError(throwable: Throwable,
                              actionButtonText: String? = null,
                              action: ((view: View) -> Unit)? = null) {
    with(binding) {
        setImage(placeholderImageOf(throwable))
        imageView.isVisible = true

        messageTextView.setText(throwable.localizedErrorMessage())
        messageTextView.isVisible = true

        actionButton.text = actionButtonText
        actionButton.setOnClickListener(action)
        actionButton.isVisible = actionButtonText != null || action != null
    }
}

/**
 * Finds corresponding [CorePlaceholder.Image] for passed Throwable.
 */
fun placeholderImageOf(throwable: Throwable): CorePlaceholder.Image {
    return when (throwable) {
        is NoInternetException -> CorePlaceholder.Image(
            R.drawable.ic_placeholder_no_internet,
            "1:1"
        )
        is NetworkException -> CorePlaceholder.Image(R.drawable.ic_placeholder_no_internet, "1:1")
        else -> CorePlaceholder.Image(R.drawable.ic_placeholder_unknown_error, "1:1")
    }
}