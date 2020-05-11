package com.extensions.android_test.matchers

import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class MaxLengthMatcher(private val maxLength: Int) :
    BoundedMatcher<View, TextView>(TextView::class.java) {

    companion object {
        fun withMaxLength(maxLength: Int) = MaxLengthMatcher(maxLength)
    }

    override fun matchesSafely(view: TextView): Boolean {
        val previousText = view.text

        view.text = String().padStart(maxLength + 1, 'x')

        val resultLength = view.text.length

        view.text = previousText

        return resultLength == maxLength
    }

    override fun describeTo(description: Description) {
        description.appendText("with max length: $maxLength")
    }
}