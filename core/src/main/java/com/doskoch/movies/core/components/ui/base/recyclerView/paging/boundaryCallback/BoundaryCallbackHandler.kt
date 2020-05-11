package com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

class BoundaryCallbackHandler<T>(
    private val data: MutableLiveData<BoundaryCallbackResult<T>>
) : PagedList.BoundaryCallback<T>() {

    override fun onZeroItemsLoaded() {
        data.value = BoundaryCallbackResult.OnZeroItemsLoaded()
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        data.value = BoundaryCallbackResult.OnItemAtFrontLoaded(itemAtFront)
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        data.value = BoundaryCallbackResult.OnItemAtEndLoaded(itemAtEnd)
    }
}