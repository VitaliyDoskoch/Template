package com.extensions.android.functions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**
 * Compares to another [Drawable] based on [Drawable.ConstantState] and [Bitmap] equality.
 */
fun <D : Drawable> D?.isEqualTo(other: Drawable?): Boolean {
    return this === other || if (this != null && other != null) {
        this.constantState == other.constantState || this.toBitmap().sameAs(other.toBitmap())
    } else {
        this == other
    }
}

/**
 * Converts [Drawable] to [Bitmap].
 * @param [intrinsicWidth] width of resulting [Bitmap], if not set. 100 by default.
 * @param [intrinsicHeight] height if resulting [Bitmap], if not set. 100 by default.
 */
fun <D : Drawable> D.toBitmap(intrinsicWidth: Int = 100, intrinsicHeight: Int = 100): Bitmap {
    return if (this is BitmapDrawable) {
        bitmap
    } else {
        val bitmap = Bitmap.createBitmap(
            intrinsicWidth.let { if (it > 0) it else intrinsicWidth },
            intrinsicHeight.let { if (it > 0) it else intrinsicHeight },
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        return bitmap
    }
}