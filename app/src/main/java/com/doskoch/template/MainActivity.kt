package com.doskoch.template

import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.doskoch.template.core.components.theme.BasicTheme
import com.doskoch.template.core.components.theme.WithDimensions
import com.doskoch.template.di.AppInjector
import com.doskoch.template.error.GlobalErrorHandlerImpl
import com.doskoch.template.navigation.MainNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var globalErrorHandler: GlobalErrorHandlerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(
            ComposeView(this).apply {
                setContent {
                    WithDimensions {
                        BasicTheme {
                            MainNavGraph()
                        }
                    }
                }
            }
        )

        observeGlobalErrorHandler()
    }

    private fun observeGlobalErrorHandler() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            val context = this@MainActivity

            globalErrorHandler.events.collect { error ->
                Toast.makeText(context, error.localizedMessage(context), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
