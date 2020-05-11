package com.doskoch.movies.core.components.ui.base.recyclerView.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.doskoch.movies.core.R
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState.Failure
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState.Loading
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState.Success

abstract class BasePagedRecyclerAdapter<F : FetchState, V, H : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AsyncPagedListDiffer.PagedListListener<V> {

    companion object {
        const val FETCH_ITEM_VIEW_TYPE = -1
    }

    interface FetchItemListener<F> {
        fun onFetchItemClicked(position: Int, itemView: View, fetchState: F)
    }

    abstract val itemCallback: DiffUtil.ItemCallback<V>

    var fetchItemListener: FetchItemListener<F>? = null

    abstract fun onCreateRegularViewHolder(parent: ViewGroup, viewType: Int): H
    abstract fun onBindRegularViewHolder(holder: H, position: Int, payloads: MutableList<Any>?)

    open fun regularItemViewType(position: Int): Int = 0

    var recyclerView: RecyclerView? = null
        private set

    var beforeFetchState: F? = null
        set(value) {
            updateFetchItemDisplaying(beforeItemPosition, field, value)
            field = value
        }

    var afterFetchState: F? = null
        set(value) {
            updateFetchItemDisplaying(afterItemPosition, field, value)
            field = value
        }

    val asyncPagedListDiffer by lazy {
        AsyncPagedListDiffer(this, itemCallback).also { it.addPagedListListener(this) }
    }

    private val beforeItemPosition: Int = 0
    private val afterItemPosition: Int
        get() = itemCount - 1

    private fun updateFetchItemDisplaying(position: Int, previous: F?, new: F?) {
        when (previous) {
            is Loading, is Failure -> if (new == null || new is Success) {
                notifyItemRemoved(position)
            } else {
                notifyItemChanged(position)
            }
            is Success, null -> if (new != null && new !is Success) {
                notifyItemInserted(position)
            }
        }
    }

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

    @CallSuper
    override fun onCurrentListChanged(previousList: PagedList<V>?, currentList: PagedList<V>?) {
        afterFetchState = null
        beforeFetchState = null
    }

    override fun getItemCount(): Int {
        return asyncPagedListDiffer.itemCount +
            if (shouldShowFetchItem(beforeFetchState)) 1 else 0 +
                if (shouldShowFetchItem(afterFetchState)) 1 else 0
    }

    private fun shouldShowFetchItem(state: F?): Boolean {
        return state != null && state !is Success
    }

    final override fun getItemViewType(position: Int): Int {
        return when {
            position == beforeItemPosition && shouldShowFetchItem(beforeFetchState) -> FETCH_ITEM_VIEW_TYPE
            position == afterItemPosition && shouldShowFetchItem(afterFetchState) -> FETCH_ITEM_VIEW_TYPE
            else -> regularItemViewType(
                if (shouldShowFetchItem(beforeFetchState)) position - 1 else position
            )
        }
    }

    final override fun onCreateViewHolder(parent: ViewGroup,
                                          viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FETCH_ITEM_VIEW_TYPE) {
            FetchStateViewHolder(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_fetch_state, parent, false),
                getListener = { fetchItemListener }
            )
        } else {
            onCreateRegularViewHolder(parent, viewType)
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                        position: Int,
                                        payloads: MutableList<Any>) {
        onBind(holder, position, payloads)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBind(holder, position, null)
    }

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    private fun onBind(holder: RecyclerView.ViewHolder,
                       position: Int,
                       payloads: MutableList<Any>?) {
        if (getItemViewType(position) == FETCH_ITEM_VIEW_TYPE) {
            bindFetchItemViewHolder(holder as FetchStateViewHolder<F>, position)
        } else {
            onBindRegularViewHolder(
                holder as H,
                if (shouldShowFetchItem(beforeFetchState)) position - 1 else position,
                payloads
            )
        }
    }

    private fun bindFetchItemViewHolder(holder: FetchStateViewHolder<F>, position: Int) {
        when (position) {
            beforeItemPosition -> beforeFetchState?.let { holder.bindView(it, null) }
            afterItemPosition -> afterFetchState?.let { holder.bindView(it, null) }
            else -> throw IllegalStateException("$holder is placed on unexpected position: $position")
        }
    }
}