package com.doskoch.movies.core.components.ui.base.pager

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.doskoch.movies.core.components.ui.base.pager.behavior.StatePagerAdapterBehavior
import com.doskoch.movies.core.components.ui.base.pager.itemsManager.PagerItemsManager

open class BaseStatePagerAdapter(
    fragmentManager: FragmentManager,
    behavior: StatePagerAdapterBehavior,
    items: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager, behavior.constant) {

    private val itemsManager = PagerItemsManager(
        items = items.toMutableList(),
        notifyDataSetChanged = { notifyDataSetChanged() }
    )

    override fun getItem(position: Int): Fragment {
        return itemsManager.getItem(position)
    }

    override fun getCount(): Int {
        return itemsManager.getCount()
    }

    fun getItems(): List<Fragment> {
        return itemsManager.getItems()
    }

    fun add(item: Fragment) {
        itemsManager.add(item)
    }

    fun addAll(items: List<Fragment>) {
        itemsManager.addAll(items)
    }

    fun replaceAt(position: Int, item: Fragment) {
        itemsManager.replaceAt(position, item)
    }

    fun replaceAll(items: List<Fragment>) {
        itemsManager.replaceAll(items)
    }

    fun remove(item: Fragment) {
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
            itemsManager.replaceAt(position, it as Fragment)
        }
    }
}