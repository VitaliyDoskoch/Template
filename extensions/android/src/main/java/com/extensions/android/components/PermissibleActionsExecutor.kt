package com.extensions.android.components

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * It is a helper for requesting permissions and showing their rationales.
 * @param [activity] [Activity], used to perform such operations. See
 * [ActivityCompat.requestPermissions] for further info.
 * @param [showRationale] called, when rationale should be shown.
 * The params are: (requestCode, mapOf(permission, rationale)).
 * @param [showDenial] called, when denial should be shown.
 * The params are: (requestCode, setOf(permission), grantResults).
 */
class PermissibleActionsExecutor(
    private val activity: Activity,
    private val showRationale: (Int, Map<String, String>) -> Unit,
    private val showDenial: (Int, Set<String>, IntArray) -> Unit
) {

    private val actions by lazy { mutableMapOf<Int, () -> Unit>() }

    /**
     * Performs request for permission, if it is not already granted, and invokes passed action.
     * @param [permission] [android.Manifest.permission],
     * @param [rationale] an optional rationale for the permission. Is null by default.
     * @param [requestCode] requestCode, which will be used to request permission.
     * See [ActivityCompat.requestPermissions].
     * @param [action] action, which should be called after granting permissions.
     */
    fun performWithPermission(permission: String,
                              rationale: String? = null,
                              requestCode: Int,
                              action: () -> Unit) {
        if (ContextCompat.checkSelfPermission(activity, permission) ==
            PackageManager.PERMISSION_DENIED
        ) {
            actions[requestCode] = action

            if (rationale != null &&
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            ) {
                showRationale(requestCode, mapOf(permission to rationale))
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
        } else {
            action.invoke()
        }
    }

    /**
     * Performs request for permissions, if at least one of them is not granted yet, and
     * invokes passed action.
     * @param [permissions] Map of a permission (see [android.Manifest.permission])
     * and an optional rationale for that permission.
     * @param [requestCode] requestCode, which will be used to request permission.
     * See [ActivityCompat.requestPermissions].
     * @param [action] action, which should be called after granting permissions.
     */
    fun performWithPermissions(permissions: Map<String, String?>,
                               requestCode: Int,
                               action: () -> Unit) {
        val deniedPermissions = permissions.filter { (permission, _) ->
            ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_DENIED
        }

        if (deniedPermissions.isNotEmpty()) {
            actions[requestCode] = action

            val withRationales = deniedPermissions
                .asSequence()
                .filter { (permission, rationale) ->
                    rationale != null &&
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                }
                .map { (permission, rationale) -> permission to rationale!! }
                .toMap()

            if (withRationales.isNotEmpty()) {
                showRationale(requestCode, withRationales)
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    deniedPermissions.keys.toTypedArray(),
                    requestCode
                )
            }
        } else {
            action.invoke()
        }
    }

    /**
     * Invokes previously passed action after granting permissions.
     * Should be called with parameters of [Activity.onRequestPermissionsResult].
     */
    fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<out String>,
                                   grantResults: IntArray) {
        val action = actions[requestCode]

        if (action != null) {
            actions.remove(requestCode)

            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                action.invoke()
            } else {
                showDenial(requestCode, permissions.toSet(), grantResults)
            }
        }
    }
}