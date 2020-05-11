package com.extensions.android_test.matchers

import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class MaxLinesMatcher(private val maxLines: Int) :
    BoundedMatcher<View, TextView>(TextView::class.java) {

    companion object {
        fun withMaxLines(maxLines: Int) = MaxLinesMatcher(maxLines)
    }

    override fun matchesSafely(view: TextView) = view.lineCount <= maxLines

    override fun describeTo(description: Description) {
        description.appendText("with max lines: $maxLines")
    }
}