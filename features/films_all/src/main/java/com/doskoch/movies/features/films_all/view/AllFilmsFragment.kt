package com.doskoch.movies.features.films_all.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.BasePagedRecyclerAdapter
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback.BoundaryCallbackResult.OnItemAtEndLoaded
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback.BoundaryCallbackResult.OnZeroItemsLoaded
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.fetchState.FetchState
import com.doskoch.movies.core.components.ui.views.CorePlaceholder
import com.doskoch.movies.core.functions.showError
import com.doskoch.movies.core.functions.showErrorSnackbar
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.databinding.LayoutPageContentBinding
import com.doskoch.movies.features.films.functions.showNoFilmsPlaceholder
import com.doskoch.movies.features.films.view.FilmsAdapter
import com.doskoch.movies.features.films_all.R
import com.doskoch.movies.features.films_all.databinding.FragmentAllFilmsBinding
import com.doskoch.movies.features.films_all.newModule
import com.doskoch.movies.features.films_all.viewModel.AllFilmsViewModel
import com.extensions.kotlin.components.annotations.CallsFrom
import com.extensions.lifecycle.components.State
import com.extensions.lifecycle.components.TypeSafeViewModelFactory
import com.extensions.lifecycle.functions.observeData
import com.extensions.lifecycle.functions.observeResult
import com.extensions.lifecycle.functions.viewModelLazy
import com.extensions.retrofit.components.exceptions.NoInternetException
import com.google.android.material.appbar.AppBarLayout

class AllFilmsFragment : BaseFragment<FragmentAllFilmsBinding>(),
    BasePagedRecyclerAdapter.FetchItemListener<FetchState>,
    FilmsAdapter.ActionListener {

    data class Module(
        val viewModelFactory: TypeSafeViewModelFactory<AllFilmsViewModel>,
        val shareFilm: (AllFilmsFragment, Film, (Throwable) -> Unit) -> Unit
    )

    companion object {
        private const val APP_BAR_LAYOUT_ID = "APP_BAR_LAYOUT_ID"

        fun create(@IdRes appBarLayoutId: Int) = AllFilmsFragment().apply {
            arguments = Bundle().apply { putInt(APP_BAR_LAYOUT_ID, appBarLayoutId) }
        }

        @VisibleForTesting
        var provideModule = fun AllFilmsFragment.() = newModule()
    }

    private lateinit var module: Module

    private val viewModel by viewModelLazy { module.viewModelFactory }

    private val appBarLayout: AppBarLayout?
        get() = parentFragment?.view?.findViewById(arguments!!.getInt(APP_BAR_LAYOUT_ID))

    private val adapter: FilmsAdapter?
        get() = nestedBinding?.recyclerView?.adapter as? FilmsAdapter

    private var nestedBinding: LayoutPageContentBinding? = null

    private var parallaxListener: AppBarLayout.OnOffsetChangedListener? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentAllFilmsBinding.inflate(inflater).also {
            nestedBinding =
                LayoutPageContentBinding.bind(it.root.findViewById(R.id.nestedScrollView))
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::module.isInitialized) {
            module = provideModule()
        }

        initViews()
    }

    private fun initViews() {
        initParallax()

        contentManager?.apply {
            initViews(R.id.progressView, R.id.recyclerView, R.id.placeholder)

            progressListeners.add { show ->
                viewBinding?.swipeRefreshLayout?.isEnabled = !show
            }
        }

        viewBinding?.swipeRefreshLayout?.apply {
            isEnabled = false
            setOnRefreshListener(this@AllFilmsFragment::onRefresh)
        }

        nestedBinding?.let { initRecyclerView(it.recyclerView) }
    }

    private fun initParallax() {
        parallaxListener = AppBarLayout.OnOffsetChangedListener { layout, offset ->
            nestedBinding?.progressView?.translationY = (layout.totalScrollRange + offset) / -2f
            nestedBinding?.placeholder?.translationY = (layout.totalScrollRange + offset) / -2f
        }

        appBarLayout?.addOnOffsetChangedListener(parallaxListener)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            isMotionEventSplittingEnabled = false
            layoutManager = LinearLayoutManager(context)

            adapter = FilmsAdapter().apply {
                fetchItemListener = this@AllFilmsFragment
                actionListener = this@AllFilmsFragment
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observePagedList()
        observeBoundaryCallbackData()
    }

    private fun observePagedList() {
        viewModel.pagedListData().observeData(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> contentManager?.showProgress()
                is State.Success -> submitList(state.data)
                is State.Failure -> contentManager?.showPlaceholder {
                    (it as? CorePlaceholder)?.showError(state.throwable)
                }
            }
        }
    }

    private fun submitList(list: PagedList<Film>) {
        adapter?.asyncPagedListDiffer?.submitList(list) {
            if (list.isNotEmpty()) {
                contentManager?.showContent()
            }
        }
    }

    private fun observeBoundaryCallbackData() {
        viewModel.boundaryCallbackData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is OnZeroItemsLoaded -> loadFirstPage()
                is OnItemAtEndLoaded -> loadNextPage()
            }
        })
    }

    private fun loadFirstPage() {
        viewModel.loadNextPage().observeResult(viewLifecycleOwner, ) { state ->
            when (state) {
                is State.Success -> if (state.data == 0) {
                    showNoFilmsPlaceholder()
                }
                is State.Failure -> contentManager?.showPlaceholder {
                    (it as? CorePlaceholder)?.showError(state.throwable)
                }
            }
        }
    }

    private fun loadNextPage() {
        val list = adapter?.asyncPagedListDiffer?.currentList

        if (list == null || adapter?.afterFetchState is FetchState.Loading) {
            return
        }

        val liveData = viewModel.loadNextPage()

        liveData.observeData(viewLifecycleOwner) { state ->
            adapter?.afterFetchState = when (state) {
                is State.Loading -> FetchState.Loading()
                is State.Success -> FetchState.Success()
                is State.Failure -> FetchState.Failure(state.throwable)
            }

            if (state is State.Success ||
                state is State.Failure && state.throwable.cause !is NoInternetException
            ) {
                liveData.removeObservers(viewLifecycleOwner)
            }
        }
    }

    private fun onRefresh() {
        viewModel.refresh().observeResult(viewLifecycleOwner) { state ->
            if(state !is State.Loading) {
                viewBinding?.swipeRefreshLayout?.isRefreshing = false
            }
            if(state is State.Failure) {
                view?.let { showErrorSnackbar(it, state.throwable) }
            }
        }
    }

    @CallsFrom(FilmsAdapter::class)
    override fun onFetchItemClicked(position: Int, itemView: View, fetchState: FetchState) {
        loadNextPage()
    }

    @CallsFrom(FilmsAdapter::class)
    override fun onFavouriteClicked(item: Film) {
        viewModel.switchFavourite(item).observeResult(viewLifecycleOwner) { state ->
            if (state is State.Failure) {
                view?.let { showErrorSnackbar(it, state.throwable) }
            }
        }
    }

    @CallsFrom(FilmsAdapter::class)
    override fun onShareClicked(item: Film) {
        module.shareFilm(this, item) { throwable ->
            view?.let { showErrorSnackbar(it, throwable) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appBarLayout?.removeOnOffsetChangedListener(parallaxListener)
        nestedBinding = null
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentAllFilmsBinding.inflate(inflater)
}