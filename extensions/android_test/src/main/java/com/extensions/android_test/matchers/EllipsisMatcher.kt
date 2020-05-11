package com.extensions.android_test.matchers

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class EllipsisMatcher(private val ellipsis: TextUtils.TruncateAt) :
    BoundedMatcher<View, TextView>(TextView::class.java) {

    companion object {
        fun withEllipsis(ellipsis: TextUtils.TruncateAt = TextUtils.TruncateAt.END) =
            EllipsisMatcher(ellipsis)
    }

    override fun matchesSafely(view: TextView) = view.ellipsize == ellipsis

    override fun describeTo(description: Description) {
        description.appendText("with ellipsis: ${ellipsis.name}")
    }
}