package com.extensions.android_test.functions

import androidx.annotation.RawRes
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

/**
 * Loads Json from Raw Resources.
 * NOTE: place Json files to read in androidTest resource directory.
 */
fun readRawFile(@RawRes fileRes: Int): String {
    return getInstrumentation().context.resources.openRawResource(fileRes)
        .use { it.bufferedReader().readText() }
}