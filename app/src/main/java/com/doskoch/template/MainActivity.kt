package com.doskoch.template

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.doskoch.template.core.components.error.CoreError
import com.doskoch.template.core.components.error.GlobalErrorHandler
import com.doskoch.template.core.components.ui.CoreToast
import com.doskoch.template.di.AppInjector
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        observeGlobalErrorHandler()
    }

    private fun observeGlobalErrorHandler() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            val context = this@MainActivity

            (AppInjector.component.globalErrorHandler as GlobalErrorHandlerImpl).events.collect { error ->
                CoreToast(context, error.localizedMessage(context), Toast.LENGTH_SHORT).show()
            }
        }
    }
}