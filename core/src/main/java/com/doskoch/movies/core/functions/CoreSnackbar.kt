package com.doskoch.movies.core.functions

import android.view.View
import com.doskoch.movies.core.R
import com.doskoch.movies.core.components.ui.views.CoreSnackbar
import com.doskoch.legacy.functions.getThemeColor

fun showErrorSnackbar(anchorView: View, throwable: Throwable) {
    CoreSnackbar(anchorView, throwable.localizedErrorMessage())
        .apply { backgroundColor = anchorView.context.getThemeColor(R.attr.colorError) }
        .create()
        .show()
}