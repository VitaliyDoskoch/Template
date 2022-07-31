package com.doskoch.legacy.android.functions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Menu
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.MenuItemCompat

/**
 * Finds the color, stored in the theme.
 * @throws [IllegalArgumentException] when the color with passed id is not found in the theme.
 */
@ColorInt
fun Context.getThemeColor(@AttrRes colorRes: Int): Int {
    val typedValue = TypedValue()
    if (theme.resolveAttribute(colorRes, typedValue, true)) {
        return typedValue.data
    } else {
        throw IllegalArgumentException("color with id $colorRes is not found in context theme")
    }
}

/**
 * Applies the color to the [Drawable].
 */
fun Drawable.setColor(@ColorInt color: Int) {
    when (this) {
        is ShapeDrawable -> this.paint.color = color
        is GradientDrawable -> this.color = ColorStateList.valueOf(color)
        is ColorDrawable -> this.color = color
    }
}

/**
 * Retrieves the dominant color of the [Drawable].
 * @return the most used color.
 */
@ColorInt
fun Drawable.getDominantColor(): Int {
    return when (this) {
        is ColorDrawable -> this.color
        is ShapeDrawable -> this.paint.color
        is GradientDrawable -> if (Build.VERSION.SDK_INT >= 24) {
            this.color?.defaultColor ?: getDominantColorFromPixels()
        } else {
            getDominantColorFromPixels()
        }
        else -> getDominantColorFromPixels()
    }
}

/**
 * Converts the [Drawable] to a [Bitmap] and returns the dominant color.
 */
@ColorInt
fun Drawable.getDominantColorFromPixels(): Int {
    val bitmap = this.constantState?.newDrawable()?.toBitmap() ?: this.toBitmap()
    val pixels = IntArray(bitmap.width * bitmap.height)
    bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    return pixels
        .toList()
        .groupingBy { it }
        .eachCount()
        .maxByOrNull { it.value }!!
        .key
}

/**
 * Chooses between the [lightColor] or the [darkColor] depending on the contrast with the [backgroundColor].
 * @param [backgroundColor] the background color.
 * @param [lightColor] the target color, if the background is dark.
 * @param [darkColor] the target color, if the background is light.
 */
@ColorInt
fun chooseContrastColor(@ColorInt backgroundColor: Int,
                        @ColorInt lightColor: Int,
                        @ColorInt darkColor: Int): Int {
    val background = Color.rgb(
        Color.red(backgroundColor),
        Color.green(backgroundColor),
        Color.blue(backgroundColor)
    )
    return if (ColorUtils.calculateContrast(lightColor, background) > 3) lightColor else darkColor
}
