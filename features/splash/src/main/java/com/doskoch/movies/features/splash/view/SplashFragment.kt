package com.doskoch.movies.features.splash.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.navigation.fragment.findNavController
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.features.splash.R
import com.doskoch.movies.features.splash.databinding.FragmentSplashBinding
import com.extensions.lifecycle.components.State
import com.extensions.lifecycle.functions.viewModelLazy

class SplashFragment : BaseFragment() {

    companion object {
        @VisibleForTesting
        var provideModule = fun(_: SplashFragment) = SplashFragmentModule.create()
    }

    private lateinit var module: SplashFragmentModule

    private val viewModel by viewModelLazy { module.viewModelFactory }

    private var viewBinding: FragmentSplashBinding? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentSplashBinding.inflate(inflater).also { viewBinding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::module.isInitialized) {
            module = provideModule(this)
        }

        viewBinding?.let { initViews(it) }
    }

    private fun initViews(viewBinding: FragmentSplashBinding) {
        with(viewBinding) {
            versionTextView.text = "%s %s".format(
                getString(R.string.version_shortened),
                module.versionCode
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeDisplayTimeout()
    }

    private fun observeDisplayTimeout() {
        viewModel.displayTimeoutData().observeData { state ->
            when (state) {
                is State.Success, is State.Failure -> navigateNext()
            }
        }
    }

    private fun navigateNext() {
        findNavController().navigate(module.directions.toMain())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}