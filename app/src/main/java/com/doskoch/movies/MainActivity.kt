package com.doskoch.movies

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.doskoch.movies.core.components.ui.base.activity.BaseActivity
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment
import com.doskoch.movies.dependencyInjection.ModuleScope

class MainActivity : BaseActivity() {

    private lateinit var navigationController: NavController

    private var currentScope: ModuleScope = ModuleScope.SPLASH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = supportFragmentManager
            .findFragmentById(R.id.navFragmentContainerView)!!.findNavController()
        navigationController.addOnDestinationChangedListener(this::onDestinationChanged)
    }

    private fun onDestinationChanged(controller: NavController,
                                     destination: NavDestination,
                                     args: Bundle?) {
        ModuleScope.values().forEach { scope ->
            if (destination.id in scope.destinations && currentScope != scope) {
                currentScope.injector.componentProvider?.destroyInstance()
                currentScope = scope
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.navFragmentContainerView)
            ?.childFragmentManager
            ?.fragments
            ?.firstOrNull()
            .let { fragment ->
                if ((fragment as? BaseFragment)?.onBackPressed() != true) {
                    super.onBackPressed()
                }
            }
    }
}
