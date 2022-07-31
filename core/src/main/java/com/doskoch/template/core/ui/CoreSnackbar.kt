package com.doskoch.template.core.ui

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

private const val MAX_LINES = 2

class CoreSnackbar private constructor() {

    companion object {
        fun create(anchorView: View, message: String, duration: Int) = Snackbar.make(anchorView, message, duration).apply {
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                maxLines = MAX_LINES
                ellipsize = TextUtils.TruncateAt.END
            }
        }
    }
}
