package com.extensions.android_test.matchers

import android.view.View
import android.widget.ScrollView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class ScrollBarsEnabledMatcher :
    BoundedMatcher<View, ScrollView>(ScrollView::class.java) {

    companion object {
        fun isAnyScrollBarEnabled() = ScrollBarsEnabledMatcher()
    }

    override fun matchesSafely(view: ScrollView): Boolean {
        return view.isHorizontalScrollBarEnabled || view.isVerticalScrollBarEnabled
    }

    override fun describeTo(description: Description) {
        description.appendText("with at least one scrollBar enabled")
    }
}