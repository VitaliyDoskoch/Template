package com.extensions.android_test.functions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

/**
 * Grants or revokes specified permission.
 */
@RequiresApi(Build.VERSION_CODES.M)
fun grantPermission(permission: RuntimePermission, grant: Boolean) {
    getInstrumentation().uiAutomation.executeShellCommand(
        "pm %s %s %s".format(
            if (grant) "grant" else "revoke",
            ApplicationProvider.getApplicationContext<Context>().packageName,
            permission
        )
    )
}