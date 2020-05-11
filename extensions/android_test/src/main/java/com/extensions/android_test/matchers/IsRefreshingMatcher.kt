package com.extensions.android_test.matchers

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class IsRefreshingMatcher : BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout::class.java) {

    companion object {
        fun isRefreshing() = IsRefreshingMatcher()
    }

    override fun matchesSafely(view: SwipeRefreshLayout) = view.isRefreshing

    override fun describeTo(description: Description) {
        description.appendText("is refreshing")
    }
}