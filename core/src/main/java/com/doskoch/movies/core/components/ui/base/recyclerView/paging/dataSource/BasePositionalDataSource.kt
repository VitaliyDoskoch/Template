package com.doskoch.movies.core.components.ui.base.recyclerView.paging.dataSource

import androidx.paging.PositionalDataSource

abstract class BasePositionalDataSource<T> : PositionalDataSource<T>() {

    abstract val totalCount: Int
    abstract fun get(limit: Int, offset: Int): List<T>

    open fun onLoadRangeError(throwable: Throwable) {}

    final override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        val position = computeInitialLoadPosition(params, totalCount)
        val loadSize = computeInitialLoadSize(params, position, totalCount)

        val items = get(loadSize, position)
        callback.onResult(items, position, totalCount)
    }

    final override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        try {
            val items = get(params.loadSize, params.startPosition)
            callback.onResult(items)
        } catch (throwable: Throwable) {
            onLoadRangeError(throwable)
        }
    }
}