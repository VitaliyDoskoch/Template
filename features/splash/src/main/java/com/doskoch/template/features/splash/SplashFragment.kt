package com.doskoch.template.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SplashFragment : Fragment() {

    data class Module(
        val viewModelFactory: ViewModelProvider.Factory,
        val versionCode: Int
    )

    private lateinit var module: Module

//    private val viewModel by viewModels<SplashViewModel> { module.viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(requireContext()).apply {
        setContent {
            Text("Hello, compose")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::module.isInitialized) {
            module = provideModule()
        }
    }

    companion object {
        @VisibleForTesting
        var provideModule = fun SplashFragment.() = Modules.splashFragment()
    }
}
