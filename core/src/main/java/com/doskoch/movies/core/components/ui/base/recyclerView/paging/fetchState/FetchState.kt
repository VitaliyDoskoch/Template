package com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState

sealed class FetchState {
    open class Loading : FetchState()
    open class Success : FetchState()
    open class Failure(val throwable: Throwable) : FetchState()
}