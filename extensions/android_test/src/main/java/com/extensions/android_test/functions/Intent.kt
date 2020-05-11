package com.extensions.android_test.functions

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import org.hamcrest.CoreMatchers

/**
 * Mocks external Intents to instantly return [Activity.RESULT_OK] with null result data.
 */
fun mockExternalIntentsAsSuccessful() {
    Intents.intending(CoreMatchers.not(IntentMatchers.isInternal()))
        .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
}

/**
 * Mocks all Intents to instantly return [Activity.RESULT_OK] with null result data.
 */
fun mockIntentsAsSuccessful() {
    Intents.intending(IntentMatchers.anyIntent())
        .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
}