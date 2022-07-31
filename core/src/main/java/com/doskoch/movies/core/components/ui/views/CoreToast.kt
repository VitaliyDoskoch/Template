package com.doskoch.movies.core.components.ui.views

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.doskoch.legacy.android.functions.getThemeColor
import com.doskoch.movies.core.R

class CoreToast(context: Context, message: String, duration: Int) : Toast(context) {

    constructor(context: Context, @StringRes messageRes: Int, duration: Int) :
        this(context, context.getString(messageRes), duration)

    init {
        val binding = LayoutInflater.from(context).inflate(R.layout.view_core_toast, null)

        val background = GradientDrawable().apply {
            setColor(context.getThemeColor(R.attr.colorPrimaryDark))
            cornerRadius = context.resources.getDimensionPixelSize(R.dimen.base_large_x5).toFloat()
        }

        binding.findViewById<TextView>(R.id.toastMessageTextView).apply {
            this.background = background
            this.text = message
        }

        this.view = binding
        this.duration = duration
    }
}
