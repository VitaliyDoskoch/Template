package com.doskoch.movies.core.components.ui.base.recyclerView

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {

    var recyclerView: RecyclerView? = null
        private set

    private val items: MutableList<T> = mutableListOf()

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    @CallSuper
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun getItemCount(): Int = items.size

    open fun getItem(position: Int): T = items[position]

    open fun getItems(): MutableList<T> = items.toMutableList()

    open fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    open fun addAll(items: List<T>) {
        this.items.addAll(items)
        notifyItemRangeInserted(itemCount - items.size, items.size)
    }

    open fun insertAt(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    open fun insertAll(position: Int, items: List<T>) {
        this.items.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    open fun replaceAt(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    open fun replaceAll(items: List<T>) {
        val oldSize = this.items.size
        val newSize = items.size

        this.items.apply {
            clear()
            addAll(items)
        }

        when {
            oldSize < newSize -> {
                notifyItemRangeChanged(0, oldSize)
                notifyItemRangeInserted(oldSize, newSize - oldSize)
            }
            oldSize > newSize -> {
                notifyItemRangeChanged(0, newSize)
                notifyItemRangeRemoved(newSize, oldSize - newSize)
            }
            oldSize == newSize -> notifyItemRangeChanged(0, oldSize)
        }
    }

    open fun remove(item: T) {
        val position = items.indexOf(item)
        if (items.remove(item)) {
            notifyItemRemoved(position)
        }
    }

    open fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun removeAll(items: List<T>) {
        items.forEach {
            this.items.indexOf(it).let { position ->
                if (position != -1) {
                    removeAt(position)
                }
            }
        }
    }

    open fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    /**
     * @throws NotImplementedError when child adapter does not implement [diffUtilCallback] method
     */
    open fun update(items: List<T>, detectMoves: Boolean) {
        DiffUtil.calculateDiff(diffUtilCallback(this.items, items), detectMoves).also {
            this.items.apply {
                clear()
                addAll(items)
            }
            it.dispatchUpdatesTo(this)
        }
    }

    protected open fun diffUtilCallback(oldList: List<T>, newList: List<T>): DiffUtil.Callback {
        throw NotImplementedError()
    }
}
