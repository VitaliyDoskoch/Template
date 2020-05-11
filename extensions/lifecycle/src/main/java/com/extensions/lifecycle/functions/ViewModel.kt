@file:Suppress("RemoveExplicitTypeArguments")

package com.extensions.lifecycle.functions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.extensions.lifecycle.components.TypeSafeViewModelFactory

//region FragmentActivity

inline fun <reified T : ViewModel> FragmentActivity.viewModel(key: String? = null,
                                                              factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory).let { provider ->
        if (key != null) {
            provider.get(key, T::class.java)
        } else {
            provider.get(T::class.java)
        }
    }
}

inline fun <reified T : ViewModel> FragmentActivity.viewModelLazy(key: String? = null,
                                                                  factory: ViewModelProvider.Factory): Lazy<T> {
    return lazy { viewModel<T>(key, factory) }
}

inline fun <reified T : ViewModel> FragmentActivity.viewModelLazy(key: String? = null,
                                                                  factory: TypeSafeViewModelFactory<T>): Lazy<T> {
    return lazy { viewModel<T>(key, factory) }
}

inline fun <reified T : ViewModel> FragmentActivity.viewModelLazy(key: String? = null,
                                                                  crossinline provideFactory: () -> TypeSafeViewModelFactory<T>): Lazy<T> {
    return lazy { viewModel<T>(key, provideFactory()) }
}

//endregion

//region Fragment

inline fun <reified T : ViewModel> Fragment.viewModel(key: String? = null,
                                                      factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory).let { provider ->
        if (key != null) {
            provider.get(key, T::class.java)
        } else {
            provider.get(T::class.java)
        }
    }
}

inline fun <reified T : ViewModel> Fragment.viewModelLazy(key: String? = null,
                                                          factory: ViewModelProvider.Factory): Lazy<T> {
    return lazy { viewModel<T>(key, factory) }
}

inline fun <reified T : ViewModel> Fragment.viewModelLazy(key: String? = null,
                                                          factory: TypeSafeViewModelFactory<T>): Lazy<T> {
    return lazy { viewModel<T>(key, factory) }
}

inline fun <reified T : ViewModel> Fragment.viewModelLazy(key: String? = null,
                                                          crossinline provideFactory: () -> TypeSafeViewModelFactory<out T>): Lazy<T> {
    return lazy { viewModel<T>(key, provideFactory()) }
}

//endregion