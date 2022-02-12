package com.doskoch.legacy.android.functions

import android.content.Context
import android.util.TypedValue

/**
 * Converts dp unit to equivalent pixels depending on [android.util.DisplayMetrics].
 * @param [value] value in dp.
 * @return value in px.
 */
fun Context.convertDpToPx(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        resources.displayMetrics
    ).toInt()
}

/**
 * Converts px unit to equivalent dp depending on [android.util.DisplayMetrics].
 * @param [value] value in px.
 * @return value in dp.
 */
fun Context.convertPxToDp(value: Int): Float {
    return value / resources.displayMetrics.density
}

/**
 * Converts sp unit to equivalent pixels depending on [android.util.DisplayMetrics].
 * @param [value] value in sp.
 * @return value in px.
 */
fun Context.convertSpToPx(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value,
        resources.displayMetrics
    ).toInt()
}