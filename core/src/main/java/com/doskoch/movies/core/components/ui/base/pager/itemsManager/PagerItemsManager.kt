package com.doskoch.movies.core.components.ui.base.pager.itemsManager

class PagerItemsManager<P>(
    private val items: MutableList<P>,
    private val notifyDataSetChanged: () -> Unit
) {

    fun getCount(): Int = items.size

    fun getItem(position: Int): P = items[position]

    fun getItems(): List<P> = items.toMutableList()

    fun add(item: P) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun addAll(items: List<P>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun replaceAt(position: Int, item: P) {
        items.removeAt(position)
        items.add(position, item)
        notifyDataSetChanged()
    }

    fun replaceAll(items: List<P>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun remove(item: P) {
        items.remove(item)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}