package com.doskoch.movies.core.components.ui.base.pager

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.doskoch.movies.core.components.ui.base.pager.behavior.StatePagerAdapterBehavior
import com.doskoch.movies.core.components.ui.base.pager.itemsManager.PagerItemsManager

open class BaseTabStatePagerAdapter(
    fragmentManager: FragmentManager,
    behavior: StatePagerAdapterBehavior,
    items: List<Pair<String, Fragment>> = emptyList()
) : FragmentStatePagerAdapter(fragmentManager, behavior.constant) {

    private val itemsManager = PagerItemsManager(
        items = items.toMutableList(),
        notifyDataSetChanged = { notifyDataSetChanged() }
    )

    override fun getItem(position: Int): Fragment {
        return itemsManager.getItem(position).second
    }

    override fun getCount(): Int {
        return itemsManager.getCount()
    }

    fun getItems(): List<Pair<String, Fragment>> {
        return itemsManager.getItems()
    }

    fun add(item: Pair<String, Fragment>) {
        itemsManager.add(item)
    }

    fun addAll(items: List<Pair<String, Fragment>>) {
        itemsManager.addAll(items)
    }

    fun replaceAt(position: Int, item: Pair<String, Fragment>) {
        itemsManager.replaceAt(position, item)
    }

    fun replaceAll(items: List<Pair<String, Fragment>>) {
        itemsManager.replaceAll(items)
    }

    fun remove(item: Pair<String, Fragment>) {
        itemsManager.remove(item)
    }

    fun removeAt(position: Int) {
        itemsManager.removeAt(position)
    }

    fun clear() {
        itemsManager.clear()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position).also {
            itemsManager.replaceAt(position, itemsManager.getItem(position).first to it as Fragment)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = itemsManager.getItem(position).first
}