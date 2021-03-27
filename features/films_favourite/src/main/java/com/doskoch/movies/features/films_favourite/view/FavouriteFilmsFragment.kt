package com.doskoch.movies.features.films_favourite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.VisibleForTesting
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.core.components.ui.views.CorePlaceholder
import com.doskoch.movies.core.functions.showError
import com.doskoch.movies.core.functions.showErrorSnackbar
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.databinding.LayoutPageContentBinding
import com.doskoch.movies.features.films.functions.showNoFilmsPlaceholder
import com.doskoch.movies.features.films.view.FilmsAdapter
import com.doskoch.movies.features.films_favourite.R
import com.doskoch.movies.features.films_favourite.databinding.FragmentFavouriteFilmsBinding
import com.doskoch.movies.features.films_favourite.newModule
import com.doskoch.movies.features.films_favourite.viewModel.FavouriteFilmsViewModel
import com.extensions.kotlin.components.annotations.CallsFrom
import com.extensions.lifecycle.components.State
import com.extensions.lifecycle.components.TypeSafeViewModelFactory
import com.extensions.lifecycle.functions.observeData
import com.extensions.lifecycle.functions.observeResult
import com.extensions.lifecycle.functions.viewModelLazy
import com.google.android.material.appbar.AppBarLayout

class FavouriteFilmsFragment : BaseFragment<FragmentFavouriteFilmsBinding>(), FilmsAdapter.ActionListener {

    data class Module(
        val viewModelFactory: TypeSafeViewModelFactory<FavouriteFilmsViewModel>,
        val shareFilm: (FavouriteFilmsFragment, Film, (Throwable) -> Unit) -> Unit
    )

    companion object {
        private const val APP_BAR_LAYOUT_ID = "APP_BAR_LAYOUT_ID"

        fun create(@IdRes appBarLayoutId: Int): FavouriteFilmsFragment {
            return FavouriteFilmsFragment().apply {
                arguments = Bundle().apply {
                    putInt(APP_BAR_LAYOUT_ID, appBarLayoutId)
                }
            }
        }

        @VisibleForTesting
        var provideModule = fun FavouriteFilmsFragment.() = newModule()
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
        return FragmentFavouriteFilmsBinding.inflate(inflater).also {
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

        contentManager?.initViews(R.id.progressView, R.id.recyclerView, R.id.placeholder)

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
                actionListener = this@FavouriteFilmsFragment
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observePagedList()
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
            } else {
                showNoFilmsPlaceholder()
            }
        }
    }

    @CallsFrom(FilmsAdapter::class)
    override fun onFavouriteClicked(item: Film) {
        viewModel.delete(item).observeResult(viewLifecycleOwner) { state ->
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

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentFavouriteFilmsBinding.inflate(inflater)
}