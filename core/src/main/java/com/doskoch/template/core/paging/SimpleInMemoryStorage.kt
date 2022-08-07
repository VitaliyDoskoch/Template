package com.doskoch.template.core.paging

class SimpleInMemoryStorage<K, V> {

    private val invalidationCallbacks = mutableListOf<() -> Unit>()

    private val storage = mutableMapOf<PageKeys<K>, List<V>>()

    val items: List<V>
        get() = storage.values.flatten()

    val lastKeys: PageKeys<K>?
        get() = storage.keys.toList().lastOrNull()

    fun addInvalidationCallback(callback: () -> Unit) {
        invalidationCallbacks.add(callback)
    }

    fun removeInvalidationCallback(callback: () -> Unit) {
        invalidationCallbacks.remove(callback)
    }

    fun store(prevKey: K?, nextKey: K?, page: List<V>) {
        storage[PageKeys(prevKey, nextKey)] = page
        triggerInvalidation()
    }

    fun clear() {
        storage.clear()
        triggerInvalidation()
    }

    fun updateItem(old: V, new: V) {
        storage.toList()
            .find { (_, items) -> items.contains(old) }
            ?.let { (keys, items) -> storage[keys] = items.toMutableList().apply { set(indexOf(old), new) } }
        triggerInvalidation()
    }

    private fun triggerInvalidation() {
        invalidationCallbacks.forEach { it.invoke() }
    }

    data class PageKeys<K>(val prev: K?, val next: K?)
}