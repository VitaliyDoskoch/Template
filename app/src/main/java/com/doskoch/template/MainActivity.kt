package com.doskoch.template

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.doskoch.template.core.error.CoreError
import com.doskoch.template.core.error.GlobalErrorHandler
import com.doskoch.template.core.ui.CoreToast
import com.doskoch.template.di.AppInjector

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        initGlobalErrorHandler()
    }

    private fun initGlobalErrorHandler() {
        AppInjector.component.globalErrorHandlerHolder.handler = object : GlobalErrorHandler {
            val context = this@MainActivity

            override fun showError(error: CoreError) {
                CoreToast(context, error.localizedMessage(context), Toast.LENGTH_SHORT).show()
            }
        }
    }
}