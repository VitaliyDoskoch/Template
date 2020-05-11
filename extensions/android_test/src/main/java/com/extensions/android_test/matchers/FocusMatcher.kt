package com.extensions.android_test.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class FocusMatcher : BoundedMatcher<View, View>(View::class.java) {

    companion object {
        fun isFocused() = FocusMatcher()
    }

    override fun matchesSafely(view: View) = view.isFocused

    override fun describeTo(description: Description) {
        description.appendText("is focused")
    }
}