package com.doskoch.legacy.android.functions

import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView
import java.util.*

/**
 * Applies the maximum length to the [TextView].
 */
fun <V : TextView> V.setMaxLength(maxLength: Int) {
    val existingFilters = this.filters
    val newFilters = ArrayList<InputFilter>()
    for (filter in existingFilters) {
        if (filter !is InputFilter.LengthFilter) {
            newFilters.add(filter)
        }
    }
    newFilters.add(InputFilter.LengthFilter(maxLength))

    this.filters = newFilters.toTypedArray()
}

/**
 * Determines whether the [TextView] displays its content as masked password.
 * See [InputType].
 */
fun <V : TextView> V.isPasswordMasked(): Boolean {
    return inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_NUMBER_VARIATION_PASSWORD) ||
        inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) ||
        inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD)
}

/**
 * Switches the password visibility of the [TextView]. See [InputType].
 * @param [show] whether the password should be visible.
 */
fun <V : TextView> V.switchPasswordVisibility(show: Boolean) {
    val typeface = typeface
    val cursorPosition = selectionEnd

    inputType = if (show) {
        InputType.TYPE_CLASS_TEXT
    } else {
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    setTypeface(typeface)

    if (this is EditText) {
        setSelection(cursorPosition)
    }
}

/**
 * Adds a [ViewTreeObserver.OnGlobalLayoutListener] to the [View]. When an event is triggered,
 * invokes the [action] and removes the [ViewTreeObserver.OnGlobalLayoutListener].
 */
fun <V : View> V.performOnceOnGlobalLayout(action: (view: V) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            action.invoke(this@performOnceOnGlobalLayout)
        }
    })
}

/**
 * Checks if the [TextView] is ellipsized.
 */
fun <V : TextView> V.isEllipsized(): Boolean {
    for (i in 0 until layout.lineCount) {
        if (layout.getEllipsisCount(i) > 0) {
            return true
        }
    }

    return false
}
