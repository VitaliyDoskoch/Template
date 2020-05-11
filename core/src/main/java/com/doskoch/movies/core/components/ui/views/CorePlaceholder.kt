package com.doskoch.movies.core.components.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.doskoch.movies.core.databinding.ViewCorePlaceholderBinding

class CorePlaceholder(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    data class Image(@DrawableRes val drawableRes: Int, val ratio: String)

    val binding: ViewCorePlaceholderBinding = ViewCorePlaceholderBinding
        .inflate(LayoutInflater.from(context), this)

}