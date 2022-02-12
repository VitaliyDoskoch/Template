package com.doskoch.legacy.android.functions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap

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