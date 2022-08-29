package com.doskoch.template.core.components.paging

@Suppress("MemberVisibilityCanBePrivate")
class SimpleInMemoryStorage<K, V> {

    private val invalidationCallbacks = mutableListOf<() -> Unit>()

    private val storage = mutableMapOf<PageKeys<K>, List<V>>()

    val items: List<V>
        get() = storage.values.flatten()

    val keys: Set<PageKeys<K>>
        get() = storage.keys

    fun addInvalidationCallback(callback: () -> Unit) {
        invalidationCallbacks.add(callback)
    }

    fun removeInvalidationCallback(callback: () -> Unit) {
        invalidationCallbacks.remove(callback)
    }

    fun keysOf(key: K) = keys.find { it.current == key }

    fun pageOf(key: K) = keys
        .find { it.current == key }
        ?.let { storage[it] }

    fun store(previousKey: K?, currentKey: K, nextKey: K?, page: List<V>) = synchronized(storage){
        storage[PageKeys(previousKey, currentKey, nextKey)] = page
        triggerInvalidation()
    }

    fun clear() = synchronized(storage){
        storage.clear()
        triggerInvalidation()
    }

    fun replace(old: V, new: V) {
        var index = -1
        val page = storage.toList().find { (_, items) ->
            index = items.indexOf(old)
            index != -1
        }

        synchronized(storage) {
            page?.let { (keys, items) ->
                storage[keys] = items.toMutableList().apply { set(index, new) }
            }
            triggerInvalidation()
        }
    }

    private fun triggerInvalidation() {
        invalidationCallbacks.toMutableList().forEach { it.invoke() }
    }

    data class PageKeys<K>(val previous: K?, val current: K, val next: K?)
}