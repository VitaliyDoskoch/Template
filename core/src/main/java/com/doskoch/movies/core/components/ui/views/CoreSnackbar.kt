package com.doskoch.movies.core.components.ui.views

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.doskoch.movies.core.R
import com.google.android.material.snackbar.Snackbar

class CoreSnackbar(var anchorView: View, var message: String) {

    companion object {
        private const val MAX_LINES = 2
    }

    var duration: Int = Snackbar.LENGTH_LONG

//    @ColorInt
//    var textColor: Int = anchorView.context.getThemeColor(R.attr.colorTextPrimaryInverse)
//
//    @ColorInt
//    var backgroundColor: Int = anchorView.context.getThemeColor(R.attr.colorAccentDark)

    constructor(anchorView: View, @StringRes messageRes: Int) :
        this(anchorView, anchorView.context.getString(messageRes))

    fun create(): Snackbar {
        return Snackbar.make(anchorView, message, duration).apply {
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                maxLines = MAX_LINES
                ellipsize = TextUtils.TruncateAt.END
                setTextColor(textColors)
            }

//            view.setBackgroundColor(backgroundColor)
        }
    }
}
