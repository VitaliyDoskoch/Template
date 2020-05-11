package com.extensions.android.functions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Shows software keyboard.
 */
fun <A : Activity> A.showSoftKeyboard() {
    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

/**
 * Shows software keyboard.
 */
fun <V : View> V.showSoftKeyboard() {
    if (requestFocus()) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Hides software keyboard on [View], to which the keyboard is attached.
 */
fun <V : View> V.hideSoftKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Closes keyboard on touch outside of any [EditText] in view.
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