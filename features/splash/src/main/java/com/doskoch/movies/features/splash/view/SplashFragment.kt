package com.doskoch.movies.features.splash.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.doskoch.movies.features.splash.R
import com.doskoch.movies.features.splash.SplashFeature
import com.doskoch.movies.features.splash.databinding.FragmentSplashBinding
import com.doskoch.movies.features.splash.splashFragmentDependencies
import com.doskoch.movies.features.splash.viewModel.SplashViewModel

class SplashFragment : Fragment() {

    data class Dependencies(
        val viewModelFactory: ViewModelProvider.Factory,
        val directions: SplashFeature.Directions,
        val versionCode: Int
    )

    companion object {
        @VisibleForTesting
        var provideModule = fun SplashFragment.() = splashFragmentDependencies()
    }

    private lateinit var dependencies: Dependencies

    private val viewModel by viewModels<SplashViewModel> { dependencies.viewModelFactory }

    var viewBinding: FragmentSplashBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateViewBinding(inflater).also { viewBinding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::dependencies.isInitialized) {
            dependencies = provideModule()
        }

        viewBinding?.let { initViews(it) }
    }

    private fun initViews(viewBinding: FragmentSplashBinding) {
        with(viewBinding) {
            versionTextView.text = "%s %s".format(
                getString(R.string.version_shortened),
                dependencies.versionCode
            )
        }
    }

    private fun navigateNext() {
        findNavController().navigate(dependencies.directions.toMain())
    }

    fun inflateViewBinding(inflater: LayoutInflater) = FragmentSplashBinding.inflate(inflater)
}