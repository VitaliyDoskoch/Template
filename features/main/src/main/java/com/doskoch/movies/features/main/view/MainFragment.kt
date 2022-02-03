package com.doskoch.movies.features.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.core.components.ui.base.pager.BaseTabPagerAdapter
import com.doskoch.movies.core.components.ui.base.pager.behavior.PagerAdapterBehavior.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.doskoch.movies.features.main.R
import com.doskoch.movies.features.main.databinding.FragmentMainBinding
import com.doskoch.movies.features.main.mainFragmentDependencies

class MainFragment : BaseFragment<FragmentMainBinding>() {

    data class Module(val provideAllFilmsFragment: () -> Fragment, val provideFavouriteFilmsFragment: () -> Fragment)

    companion object {
        @VisibleForTesting
        var provideModule = fun MainFragment.() = mainFragmentDependencies()
    }

    private lateinit var module: Module

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::module.isInitialized) {
            module = provideModule()
        }

        viewBinding?.let { initViews(it) }
    }

    private fun initViews(viewBinding: FragmentMainBinding) {
        with(viewBinding) {
            initViewPager(viewPager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun initViewPager(viewPager: ViewPager) {
        viewPager.adapter = BaseTabPagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            listOf(
                getString(R.string.films) to module.provideAllFilmsFragment(),
                getString(R.string.favourites) to module.provideFavouriteFilmsFragment()
            )
        )
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)
}