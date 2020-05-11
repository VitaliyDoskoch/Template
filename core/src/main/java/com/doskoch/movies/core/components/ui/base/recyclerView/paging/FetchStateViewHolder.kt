package com.doskoch.movies.core.components.ui.base.recyclerView.paging

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.doskoch.movies.core.components.ui.base.recyclerView.BaseViewHolder
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState.Failure
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState.Loading
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState.Success
import com.doskoch.movies.core.databinding.ListItemFetchStateBinding
import com.doskoch.movies.core.functions.localizedErrorMessage

class FetchStateViewHolder<F : FetchState>(
    itemView: View,
    private val getListener: () -> BasePagedRecyclerAdapter.FetchItemListener<F>?
) : BaseViewHolder<F>(itemView),
    View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onBindView(item: F, payload: MutableList<Any>?) {
        val viewBinding = ListItemFetchStateBinding.bind(itemView)

        when (item) {
            is Loading -> showLoading(viewBinding)
            is Success -> showSuccess()
            is Failure -> showFailure(viewBinding, item.throwable)
        }
    }

    private fun showLoading(viewBinding: ListItemFetchStateBinding) {
        with(viewBinding) {
            root.isEnabled = false
            progressView.visibility = VISIBLE
            refreshImageView.visibility = INVISIBLE
            errorTextView.visibility = INVISIBLE
        }
    }

    @Throws(IllegalStateException::class)
    private fun showSuccess(): Nothing {
        throw IllegalStateException("Unexpected FetchState: $item")
    }

    private fun showFailure(viewBinding: ListItemFetchStateBinding, throwable: Throwable) {
        with(viewBinding) {
            root.isEnabled = true
            progressView.visibility = INVISIBLE
            refreshImageView.visibility = VISIBLE
            errorTextView.visibility = VISIBLE
            errorTextView.text = root.context.getString(throwable.localizedErrorMessage())
        }
    }

    override fun onClick(v: View?) {
        position?.let { getListener()?.onFetchItemClicked(it, itemView, item) }
    }
}