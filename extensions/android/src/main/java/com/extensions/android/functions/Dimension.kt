package com.extensions.android.functions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import com.extensions.android.R

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

/**
 * Retrieves corresponding [Float] value.
 * @param [dimensionRes] resource id.
 * @return [Float] value resolved from resource id.
 */
@Dimension
fun Resources.getFloat(@DimenRes dimensionRes: Int): Float {
    return TypedValue().run {
        this@getFloat.getValue(dimensionRes, this, true)
        float
    }
}

/**
 * Checks if current device is Tablet.
 * @return true, if device is tablet.
 */
fun Context.isTablet(): Boolean {
    return resources.getBoolean(R.bool.isTablet)
}