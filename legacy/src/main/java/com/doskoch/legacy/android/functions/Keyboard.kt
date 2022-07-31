package com.doskoch.legacy.android.functions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Shows the software keyboard.
 */
fun <A : Activity> A.showSoftKeyboard() {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

/**
 * Shows the software keyboard.
 */
fun <V : View> V.showSoftKeyboard() {
    if (requestFocus()) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Hides the software keyboard on the [View], to which the keyboard is attached.
 */
fun <V : View> V.hideSoftKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Closes the keyboard on a touch outside of any [EditText] in the View.
 */
@SuppressLint("ClickableViewAccessibility")
fun <V : View> V.hideKeyboardOnTouch() {
    if (this !is EditText) {
        setOnTouchListener { v, _ ->
            v.hideSoftKeyboard()
            false
        }
    }
    if (this is ViewGroup) {
        for (i in 0 until childCount) {
            val innerView = getChildAt(i)
            innerView.hideKeyboardOnTouch()
        }
    }
}