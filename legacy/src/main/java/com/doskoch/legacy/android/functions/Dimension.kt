package com.doskoch.legacy.android.functions

import android.content.Context
import android.util.TypedValue

/**
 * Converts the dp unit to the equivalent px unit depending on [android.util.DisplayMetrics].
 */
fun Context.dpToPx(value: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics).toInt()
}

/**
 * Converts the px unit to the equivalent dp unit depending on [android.util.DisplayMetrics].
 */
fun Context.pxToDp(value: Int): Float {
    return value / resources.displayMetrics.density
}

/**
 * Converts the sp unit to the equivalent px unit depending on [android.util.DisplayMetrics].
 */
fun Context.spToPx(value: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, resources.displayMetrics).toInt()
}
