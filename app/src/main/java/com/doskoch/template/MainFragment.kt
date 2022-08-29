package com.doskoch.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.doskoch.template.core.components.theme.BasicTheme
import com.doskoch.template.core.components.theme.WithDimensions
import com.doskoch.template.navigation.MainNavGraph

class MainFragment : Fragment() {

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            WithDimensions {
                BasicTheme {
                    MainNavGraph()
                }
            }
        }
    }
}