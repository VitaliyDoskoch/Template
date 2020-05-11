package com.extensions.lifecycle.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class TypeSafeViewModelFactory<V : ViewModel> : ViewModelProvider.Factory {

    abstract fun create(): V

    @Suppress("UNCHECKED_CAST")
    final override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return create() as T
    }
}