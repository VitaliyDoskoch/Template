package com.extensions.android_test.matchers

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class ImageViewSrcMatcher private constructor(
    private val drawable: Drawable?,
    @DrawableRes
    private val drawableRes: Int?
) : BoundedMatcher<View, ImageView>(ImageView::class.java) {

    companion object {
        fun withImage(drawable: Drawable?) = ImageViewSrcMatcher(drawable)
        fun withImage(@DrawableRes drawableRes: Int) = ImageViewSrcMatcher(drawableRes)
    }

    constructor(drawable: Drawable?) : this(drawable, null)
    constructor(@DrawableRes drawableRes: Int) : this(null, drawableRes)

    override fun matchesSafely(view: ImageView): Boolean {
        val expected = if (drawableRes != null) {
            ContextCompat.getDrawable(view.context, drawableRes)
        } else {
            drawable
        }

        return expected.isEqualTo(view.drawable)
    }

    private fun <D : Drawable> D?.isEqualTo(other: Drawable?): Boolean {
        return this === other || if (this != null && other != null) {
            this.constantState == other.constantState || this.toBitmap().sameAs(other.toBitmap())
        } else {
            this == other
        }
    }

    private fun <D : Drawable> D.toBitmap(): Bitmap {
        return if (this is BitmapDrawable) {
            bitmap
        } else {
            val bitmap = Bitmap.createBitmap(
                intrinsicWidth.let { if (it > 0) it else 100 },
                intrinsicHeight.let { if (it > 0) it else 100 },
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            return bitmap
        }
    }

    override fun describeTo(description: Description) {
        if (drawableRes != null) {
            description.appendText("with drawable resource: $drawableRes")
        } else {
            description.appendText("with drawable: $drawable")
        }
    }
}