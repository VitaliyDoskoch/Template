package com.doskoch.legacy.android.permission

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager

class PermissionManager(
    private val fragmentManager: FragmentManager,
    var listener: PermissionListener? = null
) {

    companion object {
        private const val TAG = "PermissionManagerFragment"
    }

    interface PermissionListener {
        fun onGranted(requestCode: Int, permissions: Array<out String>) {}
        fun onShowRationale(requestCode: Int, permissions: Array<out String>) {}
        fun onDenied(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}
    }

    init {
        findFragment()?.listener = this::onRequestPermissionsResult
    }

    fun requestPermissions(
        permissions: Array<out String>,
        requestCode: Int,
        checkRationale: Boolean
    ) {
        val fragment = findFragment() ?: newFragment()

        val denied = permissions.filter {
            ContextCompat.checkSelfPermission(fragment.requireContext(), it) == PackageManager.PERMISSION_DENIED
        }

        if (denied.isNotEmpty()) {
            if (checkRationale && denied.any { fragment.shouldShowRequestPermissionRationale(it) }) {
                removeFragment(fragment)
                listener?.onShowRationale(requestCode, permissions)
            } else {
                fragment.requestPermissions(permissions, requestCode)
            }
        } else {
            removeFragment(fragment)
            listener?.onGranted(requestCode, permissions)
        }
    }

    private fun onRequestPermissionsResult(
        fragment: PermissionManagerFragment,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        removeFragment(fragment)

        val denied = permissions.filterIndexed { index, _ ->
            grantResults[index] == PackageManager.PERMISSION_DENIED
        }

        if (denied.isEmpty()) {
            listener?.onGranted(requestCode, permissions)
        } else {
            listener?.onDenied(requestCode, permissions, grantResults)
        }
    }

    private fun findFragment(): PermissionManagerFragment? {
        return fragmentManager.findFragmentByTag(TAG) as? PermissionManagerFragment
    }

    private fun newFragment(): PermissionManagerFragment {
        return PermissionManagerFragment(this::onRequestPermissionsResult).also {
            fragmentManager
                .beginTransaction()
                .add(it, TAG)
                .commitNow()
        }
    }

    private fun removeFragment(fragment: PermissionManagerFragment) {
        fragmentManager
            .beginTransaction()
            .remove(fragment)
            .commitNow()
    }
}
