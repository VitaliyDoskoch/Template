package com.extensions.android.components.permission

import androidx.fragment.app.Fragment

internal class PermissionManagerFragment(
    var listener: ((PermissionManagerFragment, Int, Array<out String>, IntArray) -> Unit)? = null
) : Fragment() {

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        listener?.invoke(this, requestCode, permissions, grantResults)
    }
}