package com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback

sealed class BoundaryCallbackResult<T> {

    open class OnZeroItemsLoaded<T> : BoundaryCallbackResult<T>()

    open class OnItemAtFrontLoaded<T>(val itemAtFront: T) : BoundaryCallbackResult<T>()

    open class OnItemAtEndLoaded<T>(val itemAtEnd: T) : BoundaryCallbackResult<T>()
}