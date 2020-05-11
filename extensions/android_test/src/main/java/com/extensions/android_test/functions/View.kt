package com.extensions.android_test.functions

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.extensions.android_test.R
import org.hamcrest.CoreMatchers

/**
 * @return [ViewInteraction] of Home Button.
 */
fun onHomeButton(): ViewInteraction {
    return Espresso.onView(
        CoreMatchers.anyOf(
            ViewMatchers.withContentDescription(R.string.abc_action_bar_up_description),
            ViewMatchers.withContentDescription(R.string.abc_action_bar_home_description)
        )
    )
}

/**
 * @return [ViewInteraction] of [com.google.android.material.snackbar.Snackbar].
 */
fun onSnackbar(): ViewInteraction {
    return Espresso.onView(ViewMatchers.withId(R.id.snackbar_text))
}