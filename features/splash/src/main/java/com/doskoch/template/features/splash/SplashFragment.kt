package com.doskoch.template.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

class SplashFragment : Fragment() {

    data class Module(
        val viewModelFactory: ViewModelProvider.Factory,
        val versionCode: Int
    )

    private lateinit var module: Module

    private val viewModel by viewModels<SplashViewModel> { module.viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container)
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
