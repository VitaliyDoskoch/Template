package com.doskoch.template.core.components.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.doskoch.legacy.android.functions.dpToPx
import com.doskoch.template.core.R

@SuppressLint("InflateParams")
class CoreToast(context: Context, message: String, duration: Int) : Toast(context) {

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.view_core_toast, null)

        val background = GradientDrawable().apply {
            setColor(ContextCompat.getColor(context, android.R.color.background_dark))
            shape = GradientDrawable.RECTANGLE.apply { cornerRadius = context.dpToPx(24f).toFloat() }
        }

        layout.findViewById<TextView>(R.id.toastMessageTextView).apply {
            this.background = background
            this.text = message
            this.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        }

        this.view = layout
        this.duration = duration
    }
}
