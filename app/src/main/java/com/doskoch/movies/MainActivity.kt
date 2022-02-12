package com.doskoch.movies

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.doskoch.movies.core.components.ui.base.activity.BaseActivity
import com.doskoch.movies.core.components.ui.base.fragment.BaseFragment

class MainActivity : BaseActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = supportFragmentManager
            .findFragmentById(R.id.navFragmentContainerView)!!.findNavController()
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.navFragmentContainerView)
            ?.childFragmentManager
            ?.fragments
            ?.firstOrNull()
            .let { fragment ->
                if ((fragment as? BaseFragment<*>)?.onBackPressed() != true) {
                    super.onBackPressed()
                }
            }
    }
}
