package com.doskoch.legacy.android.view.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: T
        private set

    val position: Int?
        get() = adapterPosition.let { if (it != RecyclerView.NO_POSITION) it else null }

    abstract fun onBindView(item: T, payload: MutableList<Any>?)

    fun bindView(item: T, payload: MutableList<Any>?) {
        this.item = item
        onBindView(item, payload)
    }
}
