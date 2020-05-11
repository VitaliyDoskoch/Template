package com.extensions.android.functions

import android.text.InputFilter
import android.text.InputType
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import java.util.*

/**
 * Applies maximum length to [TextView].
 * @param [maxLength] target maximum length.
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
 * Determines whether [TextView] displays its content as masked password.
 * See [InputType].
 */
fun <V : TextView> V.isPasswordMasked(): Boolean {
    return inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_NUMBER_VARIATION_PASSWORD) ||
        inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) ||
        inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD)
}

/**
 * Switches password visibility of [TextView]. See [InputType].
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
 * Adds [ViewTreeObserver.OnGlobalLayoutListener] to [View]. When event is triggered,
 * invokes [action] and removes [ViewTreeObserver.OnGlobalLayoutListener].
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
 * Creates [LayoutInflater] with particular theme.
 * @param [themeRes] resource id of target theme.
 * @return new [LayoutInflater], based on passed theme.
 */
fun LayoutInflater.styledInflater(inflater: LayoutInflater,
                                  @StyleRes themeRes: Int): LayoutInflater {
    return inflater.cloneInContext(ContextThemeWrapper(inflater.context, themeRes))
}

/**
 * Checks if [TextView] is ellipsized.
 */
fun <V : TextView> V.isEllipsized(): Boolean {
    for (i in 0 until layout.lineCount) {
        if (layout.getEllipsisCount(i) > 0) {
            return true
        }
    }

    return false
}