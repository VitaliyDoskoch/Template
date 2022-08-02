package com.doskoch.template.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.doskoch.legacy.android.functions.getThemeColor
import com.doskoch.template.core.R

@SuppressLint("InflateParams")
class CoreToast(context: Context, message: String, duration: Int) : Toast(context) {

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.view_core_toast, null)

        val background = GradientDrawable().apply {
            setColor(context.getThemeColor(R.attr.colorPrimaryDark))
        }

        layout.findViewById<TextView>(R.id.toastMessageTextView).apply {
            this.background = background
            this.text = message
        }

        this.view = layout
        this.duration = duration
    }
}
